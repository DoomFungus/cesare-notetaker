import {Component, Input, OnInit} from '@angular/core';
import {Note} from "../note/note";


@Component({
  selector: 'app-notebook',
  templateUrl: './notebook.component.html',
  styleUrls: ['./notebook.component.sass']
})
export class NotebookComponent implements OnInit {

  @Input()
  notes: Note[]

  constructor() {
  }

  ngOnInit(): void {
  }
}
