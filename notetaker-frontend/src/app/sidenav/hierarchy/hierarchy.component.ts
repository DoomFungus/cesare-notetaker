import { Component, OnInit } from '@angular/core';
import {Notebook} from "./notebook/notebook";
import * as M from "materialize-css/dist/js/materialize"
import {HierarchyService} from "./hierarchy.service";

@Component({
  selector: 'app-hierarchy',
  templateUrl: './hierarchy.component.html',
  styleUrls: ['./hierarchy.component.sass'],
})
export class HierarchyComponent implements OnInit {

  notebooks: Notebook[] = []

  constructor(private hierarchyService:HierarchyService) {

  }

  ngOnInit(): void {
    const self = this;
    this.hierarchyService.getUser("123")
      .subscribe((user) => {
        console.log(user.toString())
        for(let notebook of user.notebooks){
          this.notebooks.push(notebook)
        }
      })
    document.addEventListener('DOMContentLoaded', function() {
      const elems = document.querySelectorAll('.sidenav');
      const instances = M.Sidenav.init(elems, {});
    });
    document.addEventListener('DOMContentLoaded', function() {
      const elems = document.querySelectorAll('.collapsible');
      const instances = M.Collapsible.init(elems, {
        accordion: false,
        onOpenStart: function (element:HTMLLIElement) {
          const index = Array.prototype.indexOf.call(element.parentNode.children, element)
          self.loadData(index-1)
        }
      })
    });
  }

  loadData(index){
    this.hierarchyService.getNotebook(this.notebooks[index].id.toString())
      .subscribe((notebook) => {
        this.notebooks[index].notes = notebook.notes
      })
  }

}
