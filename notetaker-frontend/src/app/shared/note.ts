import {Attachment} from "./attachment";

export interface Note {
  id: number
  title: string
  content: string
  attachments: Attachment[]
}
