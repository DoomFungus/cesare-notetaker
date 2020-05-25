import { Component, OnInit } from '@angular/core';
import {Notebook} from "./notebook/notebook";
import * as M from "materialize-css/dist/js/materialize"

@Component({
  selector: 'app-hierarchy',
  templateUrl: './hierarchy.component.html',
  styleUrls: ['./hierarchy.component.sass'],
})
export class HierarchyComponent implements OnInit {

  notebooks: Notebook[] = []

  constructor() { }

  ngOnInit(): void {
    const self = this;
    for(let _i of [1, 2, 3]){
      const notebook = {id:_i, title:_i.toString(), notes:[]};
      this.notebooks.push(notebook)
    }
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
    for(let _n of [1, 2]){
      this.notebooks[index]
          .notes
          .push({id:_n, title:_n.toString(), content:_n.toString()})
    }
  }

}
