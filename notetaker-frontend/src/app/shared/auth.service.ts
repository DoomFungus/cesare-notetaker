import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private httpClient: HttpClient) { }

  public signIn(username: String, password: String):Observable<any>{
    const user_creds = {username: username, password: password}
    return this.httpClient.post(environment.backendUrlBase + "/signin",
      user_creds)
  }

  public signUp(username: String, password: String):Observable<any>{
    const user_creds = {username: username, password: password}
    return this.httpClient.post(environment.backendUrlBase + "/signup",
      user_creds)
  }

  public refresh(refreshToken: String):Observable<any>{
    return this.httpClient.post(environment.backendUrlBase + "/refresh",
      refreshToken, {responseType:"text"})
  }

  public logout():Observable<any>{
    return this.httpClient.post(environment.backendUrlBase + "/signout","")
  }
}
