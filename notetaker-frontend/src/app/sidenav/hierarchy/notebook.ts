import {Note} from "./note";

export interface Notebook {
  id: number
  title: string
  notes: Note[]
}
