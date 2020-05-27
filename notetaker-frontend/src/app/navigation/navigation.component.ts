import {Component, EventEmitter, Inject, Input, OnInit, Output} from '@angular/core';
import {Notebook} from "./notebook";
import {NavigationService} from "./navigation.service";

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.sass'],
})
export class NavigationComponent implements OnInit {

  @Input()
  user_id: number

  @Output()
  onNoteClickEvent = new EventEmitter<[number, string]>()

  notebooks: Notebook[] = []
  active_notebook_id:number
  new_note_title:string
  new_notebook_title:string

  deleted_notebook_id:number
  deleted_note_id:number

  constructor(private hierarchyService:NavigationService, @Inject('M') private M: any) {

  }

  ngOnInit(): void {
    const self = this;
    this.hierarchyService.getNotebooksByUser(this.user_id)
      .subscribe((notebooks) => {
        self.notebooks = notebooks
        notebooks.sort((a, b) => {return a.id - b.id})
        for(let notebook of notebooks){
          notebook.notes.sort((a, b) => {return a.id - b.id})
        }
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
      const elems = document.querySelectorAll('.nav-modal');
      const instances = self.M.Modal.init(elems, {});
    });
    document.addEventListener('DOMContentLoaded', function() {
      let elems = document.querySelectorAll('.dropdown-trigger');
      let instances = self.M.Dropdown.init(elems, {});
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

  openDeleteNotebookModal(event:Event, notebook_id){
    event.stopPropagation()
    this.deleted_notebook_id = notebook_id
    this.M.Modal.getInstance(document.getElementById("delete-notebook-modal")).open()
  }

  openDeleteNoteModal(event:Event, note_id, notebook_id){
    event.stopPropagation()
    this.deleted_note_id = note_id
    this.active_notebook_id = notebook_id
    this.M.Modal.getInstance(document.getElementById("delete-note-modal")).open()
  }

  onDeleteNotebookFinish(){
    const self = this;
    this.hierarchyService
      .deleteNotebook(this.deleted_notebook_id)
      .subscribe(value => {
        self.notebooks = self.notebooks
          .filter(element => {
            return element.id !== self.deleted_notebook_id
          })
      })
  }

  onDeleteNoteFinish(){
    const self = this;
    this.hierarchyService
      .deleteNote(this.deleted_note_id)
      .subscribe(value => {
        const notebook = self.notebooks.find((element) => element.id===self.active_notebook_id)
        notebook.notes = notebook.notes
          .filter(element => {
            return element.id !== self.deleted_note_id
          })
      })
  }
}
