import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";
import {EncryptionService} from "../shared/encryption.service";
import {Attachment} from "../shared/attachment";

const ATTACHMENT_PATH:String = "/attachment"
const CONTENT_PATH:String = "/content"

@Injectable({
  providedIn: 'root'
})
export class AttachmentsService {

  constructor(private httpClient: HttpClient, private encryptionService: EncryptionService) { }

  public async getAttachmentContent(attachment_id:string):Promise<Blob>{
    let requestUrl: string = environment.backendUrlBase + ATTACHMENT_PATH + '/' + attachment_id + CONTENT_PATH;
    return this.httpClient.get( requestUrl, {responseType:"blob"})
      .toPromise()
      .then(async data => data.text())
      .then(async data => {
        if(data.length > 0)
          return this.encryptionService.decrypt(data)
        else return data
      })
      .then(data => new Blob([data]))
  }

  public async postAttachment(note_id:number, attachment:File):Promise<Attachment>{
    const formData = new FormData();
    const enc_content = await attachment.text()
      .then(data => this.encryptionService.encrypt(data))
    const blob = new Blob([enc_content])
    const enc_name = await this.encryptionService.encrypt(attachment.name)
    formData.append("content", blob, enc_name)
    const params = new HttpParams().set("note_id", note_id.toString());
    return this.httpClient.post<Attachment>(environment.backendUrlBase + ATTACHMENT_PATH,formData, {params:params})
      .toPromise()
      .then(async data => {
        data.title = await this.encryptionService.decrypt(data.title)
        return data
      })
  }

  public deleteAttachment(attachment_id:number):Observable<any>{
    return this.httpClient.delete(environment.backendUrlBase + ATTACHMENT_PATH + '/' + attachment_id)
  }
}
