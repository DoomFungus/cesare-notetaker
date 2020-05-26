import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {CKEditorModule} from "@ckeditor/ckeditor5-angular";
import {EditorComponent} from "./editor/editor.component";
import {HeaderComponent} from "./header/header.component";
import {HierarchyComponent} from "./sidenav/hierarchy/hierarchy.component";
import {HttpClientModule} from "@angular/common/http";
import {HierarchyService} from "./sidenav/hierarchy/hierarchy.service";
import {FormsModule} from "@angular/forms";

@NgModule({
  declarations: [
    AppComponent,
    EditorComponent,
    HeaderComponent,
    HierarchyComponent
  ],
  imports: [
    BrowserModule,
    CKEditorModule,
    HttpClientModule,
    AppRoutingModule,
    FormsModule
  ],
  providers: [{
    provide:HierarchyService
  }],
  bootstrap: [AppComponent]
})
export class AppModule {}
