import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Notebook} from "./notebook";
import * as M from "materialize-css/dist/js/materialize"
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
  onNoteClickEvent = new EventEmitter<number>()

  notebooks: Notebook[] = []

  constructor(private hierarchyService:HierarchyService) {

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
      const instances = M.Sidenav.init(elems, {});
    });
    document.addEventListener('DOMContentLoaded', function() {
      const elems = document.querySelectorAll('.collapsible');
      const instances = M.Collapsible.init(elems, {accordion: false})
    });
  }

  onNoteClick(note_id:number){
    this.onNoteClickEvent.emit(note_id)
  }
}
