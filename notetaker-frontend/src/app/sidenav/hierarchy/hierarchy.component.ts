import {Component, EventEmitter, Inject, Input, OnInit, Output} from '@angular/core';
import {Notebook} from "./notebook";
import {HierarchyService} from "./hierarchy.service";

@Component({
  selector: 'app-hierarchy',
  templateUrl: './hierarchy.component.html',
  styleUrls: ['./hierarchy.component.sass'],
})
export class HierarchyComponent implements OnInit {

  @Input()
  user_id: number

  @Output()
  onNoteClickEvent = new EventEmitter<[number, string]>()

  notebooks: Notebook[] = []
  active_notebook_id:number
  new_note_title:string
  new_notebook_title:string

  constructor(private hierarchyService:HierarchyService, @Inject('M') private M: any) {

  }

  ngOnInit(): void {
    const self = this;
    this.hierarchyService.getNotebooksByUser(this.user_id)
      .subscribe((notebooks) => {
        self.notebooks = notebooks
        }
      )
    document.addEventListener('DOMContentLoaded', function() {
      const elems = document.querySelectorAll('.sidenav');
      const instances = self.M.Sidenav.init(elems, {});
    });
    document.addEventListener('DOMContentLoaded', function() {
      const elems = document.querySelectorAll('.collapsible');
      const instances = self.M.Collapsible.init(elems, {accordion: false})
    });
    document.addEventListener('DOMContentLoaded', function() {
      const elems = document.querySelectorAll('.modal');
      const instances = self.M.Modal.init(elems, {});
    });
  }

  onNoteClick(note_id:number, note_title){
    this.onNoteClickEvent.emit([note_id,note_title])
  }

  onAddNoteStart(notebook_id:number){
    this.active_notebook_id = notebook_id
  }

  onAddNoteFinish(){
    const self = this;
    console.log(self.active_notebook_id)
    console.log(self.notebooks)
    this.hierarchyService
      .postNote(this.active_notebook_id, this.new_note_title)
      .subscribe((note => {
        self.onNoteClick(note.id, note.title)
        self.notebooks.find((element) => element.id===self.active_notebook_id)
          .notes.push({id: note.id, title: note.title, content: ""})
      }))
  }

  onAddNotebookFinish(){
    const self = this;
    this.hierarchyService
      .postNotebook(this.user_id, this.new_notebook_title)
      .subscribe((notebook => {
        self.notebooks.push({id: notebook.id, title: notebook.title, notes: []})
      }))
  }
}
