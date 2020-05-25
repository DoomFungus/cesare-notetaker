import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {HierarchyModule} from "./hierarchy/hierarchy.module";
import {EditorModule} from "./editor/editor.module";
import {HeaderModule} from "./header/header.module";

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    BrowserModule,
    EditorModule,
    AppRoutingModule,
    HierarchyModule,
    HeaderModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {}
