import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../../environments/environment";

const USER_PATH:String = "/user"
const NOTEBOOK_PATH:String = "/notebook"

@Injectable()
export class HierarchyService {
  constructor(private httpClient: HttpClient) { }

  public getUser(username:string):Observable<any>{
    const params = new HttpParams().set("username", username);
    return this.httpClient.get(environment.backendUrlBase + USER_PATH, {params:params})
  }

  public getNotebook(notebook_id:string):Observable<any>{
    return this.httpClient.get(environment.backendUrlBase + NOTEBOOK_PATH + `/${notebook_id}`, {})
  }

  public getNotebooksByUser(user_id:number):Observable<any>{
    const params = new HttpParams().set("user_id", user_id.toString());
    return this.httpClient.get(environment.backendUrlBase + NOTEBOOK_PATH, {params:params})

  }
}
