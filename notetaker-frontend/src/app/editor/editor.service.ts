import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Note} from "../shared/note";
import {EncryptionService} from "../shared/encryption.service";

const NOTE_PATH:String = "/note"
const CONTENT_PATH:String = "/content"

@Injectable({
  providedIn: 'root',
})
export class EditorService {

  constructor(private httpClient: HttpClient, private encryptionService: EncryptionService) { }

  public async getNote(note_id:string):Promise<Note>{
    let requestUrl: string = environment.backendUrlBase + NOTE_PATH + '/' + note_id;
    return this.httpClient.get<Note>(requestUrl)
      .toPromise()
      .then(async data => {
        data.title = await this.encryptionService.decrypt(data.title)
        for (let attachment of data.attachments) {
          attachment.title = await this.encryptionService.decrypt(attachment.title)
        }
        return data
      })
  }

  public getNoteContent(note_id:string):Promise<string>{
    let requestUrl: string = environment.backendUrlBase + NOTE_PATH + '/' + note_id + CONTENT_PATH;
    return this.httpClient.get( requestUrl, {responseType:"blob"})
      .toPromise()
      .then(async data => data.text())
      .then(async data => {
        if(data.length > 0)
         return this.encryptionService.decrypt(data)
        else return data
      })
  }

  public async putNoteContent(note_id: string, content: string):Promise<any>{
    const enc_content = await this.encryptionService.encrypt(content)
    const blob = new Blob([enc_content])
    const formData = new FormData();
    formData.append("content", blob)
    let requestUrl: string = environment.backendUrlBase + NOTE_PATH + '/' + note_id + CONTENT_PATH;
    return this.httpClient.put(requestUrl, formData)
      .toPromise()
  }
}
