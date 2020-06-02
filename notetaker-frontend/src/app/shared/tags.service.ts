import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {EncryptionService} from "./encryption.service";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";
import {Tag} from "./tag";
import {Note} from "./note";

const TAG_PATH:String = "/tag"
const NOTE_PATH:String = "/note"

@Injectable({
  providedIn: 'root'
})
export class TagsService {

  public available_tags: Promise<Tag[]>
  private username: string
  constructor(private httpClient: HttpClient, private encryptionService: EncryptionService) { }

  public setAvailableTags(username: string):void{
    this.username = username
    const params = new HttpParams().set("username", username);
    const tags = this.httpClient.get<Tag[]>(environment.backendUrlBase + TAG_PATH,
      {params:params})
      .toPromise()
      .then(async data => {
        for(let tag of data)
          tag.name = await this.encryptionService.decrypt(tag.name)
          return data
        }
      )
    this.available_tags = tags
  }

  public async postTag(tag_name:string):Promise<Tag>{
    const new_tag = {name: await this.encryptionService.encrypt(tag_name)}
    const params = new HttpParams().set("username", this.username);
    return this.httpClient.post<Tag>(environment.backendUrlBase + TAG_PATH,
      new_tag, {params:params})
      .toPromise()
      .then(async data => {
          data.name = await this.encryptionService.decrypt(data.name)
          return data
        }
      )
  }

  public deleteTag(tag_id:number):Observable<any>{
    return this.httpClient.delete(environment.backendUrlBase + TAG_PATH + '/' + tag_id)
  }

  public async updateTags(tag_ids:number[], note_id:number):Promise<void>{
    let params = new HttpParams()
    for (const tag_id of tag_ids){
      params = params.append("tag_id", tag_id.toString());
    }
    return this.httpClient.put<void>(
      environment.backendUrlBase + NOTE_PATH + "/" + note_id.toString() + TAG_PATH,
      null,{params:params})
      .toPromise()
  }

  public async findNotesByTags(tag_ids:number[]):Promise<Note[]>{
    let params = new HttpParams()
    for (const tag_id of tag_ids){
      params = params.append("tag_id", tag_id.toString());
    }
    return this.httpClient.get<Note[]>(
      environment.backendUrlBase + NOTE_PATH, {params:params})
      .toPromise()
      .then(async data => {
        for (let note of data) {
          note.title = await this.encryptionService.decrypt(note.title)
        }
        return data
      })
  }
}
