import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {HierarchyComponent} from "./hierarchy.component";
import {NotebookComponent} from "./notebook/notebook.component";
import {HttpClientModule} from "@angular/common/http";
import {HierarchyService} from "./hierarchy.service";



@NgModule({
  declarations: [
    HierarchyComponent,
    NotebookComponent
  ],
  exports: [
    HierarchyComponent
  ],
  imports: [
    CommonModule,
    HttpClientModule
  ],
  providers: [{
    provide:HierarchyService
  }]
})
export class HierarchyModule { }
