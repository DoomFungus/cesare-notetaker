import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";
import {Notebook} from "../shared/notebook";

const NOTEBOOK_PATH:String = "/notebook"
const NOTE_PATH:String = "/note"
import { map } from 'rxjs/operators';

@Injectable()
export class NavigationService {
  constructor(private httpClient: HttpClient) { }

  public getNotebooksByUser(username:string):Observable<Notebook[]>{
    const params = new HttpParams().set("username", username);
    return this.httpClient.get<Notebook[]>(environment.backendUrlBase + NOTEBOOK_PATH, {params:params})
      // .pipe(
      //   map(data => {
      //     for(let notebook of data){
      //       notebook.title = CryptoJS.AES.decrypt(notebook.title, encryption_key);
      //     }
      //     return data
      //   })
      // )

  }

  public postNotebook(username: string, notebook_title:String):Observable<any>{
    const new_notebook = {title:notebook_title}
    const params = new HttpParams().set("username", username.toString());
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
