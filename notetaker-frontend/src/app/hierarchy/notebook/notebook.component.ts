import {Component, Input, OnInit} from '@angular/core';
import {NoteComponent} from "../note/note.component";
import {Notebook} from "./notebook";
import {Note} from "../note/note";

@Component({
  selector: 'app-notebook',
  templateUrl: './notebook.component.html',
  styleUrls: ['./notebook.component.sass']
})
export class NotebookComponent implements OnInit {

  @Input()
  notebook: Notebook

  constructor() {
  }

  loadNotes(){
    for(let _n of [1, 2]){
      this.notebook.notes.push({id:_n, title:_n.toString(), content:_n.toString()})
    }
  }

  ngOnInit(): void {
  }

}
