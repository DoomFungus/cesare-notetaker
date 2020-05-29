import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";

const ATTACHMENT_PATH:String = "/attachment"
const CONTENT_PATH:String = "/content"

@Injectable({
  providedIn: 'root'
})
export class AttachmentsService {

  constructor(private httpClient: HttpClient) { }

  public getAttachmentContent(note_id:string):Observable<any>{
    let requestUrl: string = environment.backendUrlBase + ATTACHMENT_PATH + '/' + note_id + CONTENT_PATH;
    return this.httpClient.get( requestUrl, {responseType:"blob"})
  }

  public postAttachment(note_id:number, attachment:File):Observable<any>{
    const formData = new FormData();
    formData.append("content", attachment, attachment.name)
    const params = new HttpParams().set("note_id", note_id.toString());
    return this.httpClient.post(environment.backendUrlBase + ATTACHMENT_PATH,formData, {params:params})
  }

  public deleteAttachment(attachment_id:number):Observable<any>{
    return this.httpClient.delete(environment.backendUrlBase + ATTACHMENT_PATH + '/' + attachment_id)
  }
}
