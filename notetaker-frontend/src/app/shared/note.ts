import {Attachment} from "./attachment";
import {Tag} from "./tag";

export interface Note {
  id: number
  title: string
  content: string
  attachments: Attachment[]
  tags: Tag[]
}
