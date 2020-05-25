import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {CKEditorModule} from "@ckeditor/ckeditor5-angular";
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import {HierarchyModule} from "./hierarchy/hierarchy.module";

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    CKEditorModule,
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    HierarchyModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
