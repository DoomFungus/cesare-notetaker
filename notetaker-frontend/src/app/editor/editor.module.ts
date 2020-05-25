import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {HierarchyComponent} from "../sidenav/hierarchy/hierarchy.component";
import {NotebookComponent} from "../sidenav/hierarchy/notebook/notebook.component";
import {EditorComponent} from "./editor.component";
import {CKEditorModule} from "@ckeditor/ckeditor5-angular";



@NgModule({
  declarations: [
    EditorComponent
  ],
  exports: [
    EditorComponent
  ],
  imports: [
    CKEditorModule,
    CommonModule
  ]
})
export class EditorModule { }
