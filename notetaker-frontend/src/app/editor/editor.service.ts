import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";

const NOTE_PATH:String = "/note"
const CONTENT_PATH:String = "/content"

@Injectable({
  providedIn: 'root',
})
export class EditorService {

  constructor(private httpClient: HttpClient) { }

  public getNoteContent(note_id:string):Observable<any>{
    let requestUrl: string = environment.backendUrlBase + NOTE_PATH + '/' + note_id + CONTENT_PATH;
    return this.httpClient.get( requestUrl, {responseType:"blob"})
  }
}
