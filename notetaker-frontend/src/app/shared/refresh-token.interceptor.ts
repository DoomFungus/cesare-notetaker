import {Injectable} from '@angular/core';
import {HttpClient, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable, Subscriber} from 'rxjs';
import {environment} from "../../environments/environment";
import {AuthService} from "./auth.service";
import {finalize} from "rxjs/operators";

type CallerRequest = {
  subscriber: Subscriber<any>;
  failedRequest: HttpRequest<any>;
};

@Injectable()
export class RefreshTokenInterceptor implements HttpInterceptor {

  private refreshInProgress: boolean;
  private requests: CallerRequest[] = [];

  private noAuthorization:Array<string> = [
    environment.backendUrlBase+"/signin",
    environment.backendUrlBase+"/signup",
    environment.backendUrlBase+"/refresh"
  ]

  constructor(private httpClient: HttpClient, private authService: AuthService) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    if(this.noAuthorization.includes(request.url)){
      return next.handle(request);
    }

    let observable = new Observable<HttpEvent<any>>((subscriber) => {
      let originalRequestSubscription = next.handle(request)
        .subscribe((response) => {
            subscriber.next(response);
          },
          (err) => {
            if (err.status === 401) {
              this.handleUnauthorizedError(subscriber, request);
            } else {
              subscriber.error(err);
            }
          },
          () => {
            subscriber.complete();
          });

      return () => {
        originalRequestSubscription.unsubscribe();
      };
    });
    return observable;
  }

  private handleUnauthorizedError(subscriber: Subscriber < any >, request: HttpRequest<any>) {

    this.requests.push({ subscriber, failedRequest: request });
    if(!this.refreshInProgress) {
      this.refreshInProgress = true;
      const refresh_token = localStorage.getItem("refresh_token")
      this.authService.refresh(refresh_token)
        .pipe(finalize(() => {
          this.refreshInProgress = false;
        }))
        .subscribe((authHeader) => {
          this.repeatFailedRequests(authHeader)
        },

          (err) => {
            window.location.reload()
          });
    }
  }

  private repeatFailedRequests(authHeader) {
    localStorage.setItem("access_token", authHeader)
    this.requests.forEach((c) => {
      const requestWithNewToken = c.failedRequest.clone({
        headers: c.failedRequest.headers.set('Authorization', authHeader)
      });
      this.repeatRequest(requestWithNewToken, c.subscriber);
    });
    this.requests = [];
  }

  private repeatRequest(requestWithNewToken: HttpRequest < any >, subscriber: Subscriber<any>) {
    this.httpClient.request(requestWithNewToken).subscribe((res) => {
        subscriber.next(res);
      },
      (err) => {
        if (err.status === 401) {
          this.authService.logout();
        }
        subscriber.error(err);
      },
      () => {
        subscriber.complete();
      });
  }
}
