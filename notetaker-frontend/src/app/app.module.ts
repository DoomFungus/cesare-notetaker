import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {CKEditorModule} from "@ckeditor/ckeditor5-angular";
import {EditorComponent} from "./editor/editor.component";
import {HeaderComponent} from "./header/header.component";
import {NavigationComponent} from "./navigation/navigation.component";
import {HttpClientModule} from "@angular/common/http";
import {NavigationService} from "./navigation/navigation.service";
import {FormsModule} from "@angular/forms";
import * as M from 'materialize-css/dist/js/materialize';
import { LoginComponent } from './login/login.component';

@NgModule({
  declarations: [
    AppComponent,
    EditorComponent,
    HeaderComponent,
    NavigationComponent,
    LoginComponent
  ],
  imports: [
    BrowserModule,
    CKEditorModule,
    HttpClientModule,
    AppRoutingModule,
    FormsModule
  ],
  providers: [
    {provide:NavigationService},
    {provide:'M', useValue:M}
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}
