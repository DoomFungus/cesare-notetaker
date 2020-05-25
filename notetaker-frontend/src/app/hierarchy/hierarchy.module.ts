import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {HierarchyComponent} from "./hierarchy.component";
import {NotebookComponent} from "./notebook/notebook.component";
import {NoteComponent} from "./note/note.component";



@NgModule({
  declarations: [
    HierarchyComponent,
    NotebookComponent,
    NoteComponent
  ],
  exports: [
    HierarchyComponent
  ],
  imports: [
    CommonModule
  ]
})
export class HierarchyModule { }
