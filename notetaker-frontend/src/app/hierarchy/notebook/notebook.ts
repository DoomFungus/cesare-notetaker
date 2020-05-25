import {Note} from "../note/note";

export interface Notebook {
  id: Number
  title: String
  notes: Note[]
}
