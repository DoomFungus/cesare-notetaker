import { Component, OnInit } from '@angular/core';
import {Notebook} from "./notebook/notebook";

@Component({
  selector: 'app-hierarchy',
  templateUrl: './hierarchy.component.html',
  styleUrls: ['./hierarchy.component.sass'],
})
export class HierarchyComponent implements OnInit {

  notebooks: Notebook[] = []

  constructor() { }

  ngOnInit(): void {
    for(let _i of [1, 2, 3]){
      this.notebooks.push({id:_i, title:_i.toString(), notes:[]})
    }
  }

}
