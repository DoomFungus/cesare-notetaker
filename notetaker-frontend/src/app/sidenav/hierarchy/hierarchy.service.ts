import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../../environments/environment";

const NOTEBOOK_PATH:String = "/notebook"
const NOTE_PATH:String = "/note"

@Injectable()
export class HierarchyService {
  constructor(private httpClient: HttpClient) { }

  public getNotebooksByUser(user_id:number):Observable<any>{
    const params = new HttpParams().set("user_id", user_id.toString());
    return this.httpClient.get(environment.backendUrlBase + NOTEBOOK_PATH, {params:params})
  }

  public postNotebook(user_id: number, notebook_title:String):Observable<any>{
    const new_notebook = {title:notebook_title}
    const params = new HttpParams().set("user_id", user_id.toString());
    return this.httpClient.post(environment.backendUrlBase + NOTEBOOK_PATH,
      new_notebook, {params:params})
  }

  public postNote(notebook_id:number, note_title:string):Observable<any>{
    const new_note = {title:note_title}
    const params = new HttpParams().set("notebook_id", notebook_id.toString());
    return this.httpClient.post(environment.backendUrlBase + NOTE_PATH,
      new_note, {params:params})
  }

  public deleteNotebook(notebook_id:number):Observable<any>{
    return this.httpClient.delete(environment.backendUrlBase + NOTEBOOK_PATH + '/' + notebook_id)
  }

  public deleteNote(note_id:number):Observable<any>{
    return this.httpClient.delete(environment.backendUrlBase + NOTE_PATH + '/' + note_id)
  }
}
