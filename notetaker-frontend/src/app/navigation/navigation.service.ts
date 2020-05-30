import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";
import {Notebook} from "../shared/notebook";
import { map } from 'rxjs/operators';
import {EncryptionService} from "../shared/encryption.service";
import {Note} from "../shared/note";

const NOTEBOOK_PATH:String = "/notebook"
const NOTE_PATH:String = "/note"


@Injectable()
export class NavigationService {
  constructor(private httpClient: HttpClient, private encryptionService: EncryptionService) { }

  public async getNotebooksByUser(username:string):Promise<Notebook[]>{
    const params = new HttpParams().set("username", username);
    return this.httpClient.get<Notebook[]>(environment.backendUrlBase + NOTEBOOK_PATH, {params:params})
      .toPromise()
      .then(async data => {
        for (let notebook of data) {
          notebook.title = await this.encryptionService.decrypt(notebook.title)
          for(let note of notebook.notes){
            note.title = await this.encryptionService.decrypt(note.title)
          }
        }
        return data
      })

  }

  public async postNotebook(username: string, notebook_title:string):Promise<Notebook> {
    const new_notebook = {title: await this.encryptionService.encrypt(notebook_title)}
    const params = new HttpParams().set("username", username.toString());
    return this.httpClient.post<Notebook>(environment.backendUrlBase + NOTEBOOK_PATH,
      new_notebook, {params: params})
      .toPromise()
      .then(async data => {
          data.title = await this.encryptionService.decrypt(data.title)
          return data
        }
      )
  }

  public async postNote(notebook_id:number, note_title:string):Promise<Note>{
    const new_note = {title: await this.encryptionService.encrypt(note_title)}
    const params = new HttpParams().set("notebook_id", notebook_id.toString());
    return this.httpClient.post<Note>(environment.backendUrlBase + NOTE_PATH,
      new_note, {params:params})
      .toPromise()
      .then(async data => {
          data.title = await this.encryptionService.decrypt(data.title)
          return data
        }
      )
  }

  public deleteNotebook(notebook_id:number):Observable<any>{
    return this.httpClient.delete(environment.backendUrlBase + NOTEBOOK_PATH + '/' + notebook_id)
  }

  public deleteNote(note_id:number):Observable<any>{
    return this.httpClient.delete(environment.backendUrlBase + NOTE_PATH + '/' + note_id)
  }
}
