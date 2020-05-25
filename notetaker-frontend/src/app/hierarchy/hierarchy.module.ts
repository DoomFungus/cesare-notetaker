import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {HierarchyComponent} from "./hierarchy.component";
import {NotebookComponent} from "./notebook/notebook.component";



@NgModule({
  declarations: [
    HierarchyComponent,
    NotebookComponent
  ],
  exports: [
    HierarchyComponent
  ],
  imports: [
    CommonModule
  ]
})
export class HierarchyModule { }
