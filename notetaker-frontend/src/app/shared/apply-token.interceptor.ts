import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import {environment} from "../../environments/environment";

@Injectable()
export class ApplyTokenInterceptor implements HttpInterceptor {

  private noAuthorization:Array<string> = [
    environment.backendUrlBase+"/signin",
    environment.backendUrlBase+"/signup",
    environment.backendUrlBase+"/refresh",
    environment.backendUrlBase+"/signout"
  ]

  constructor() {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    if(this.noAuthorization.includes(request.url)){
      return next.handle(request);
    }

    const accessToken = localStorage.getItem("access_token")
    let newRequest = request.clone({
      headers: request.headers.set('Authorization', accessToken)
    });
    return next.handle(newRequest);
  }
}
