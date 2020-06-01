import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {CKEditorModule} from "@ckeditor/ckeditor5-angular";
import {EditorComponent} from "./editor/editor.component";
import {HeaderComponent} from "./header/header.component";
import {NavigationComponent} from "./navigation/navigation.component";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {NavigationService} from "./navigation/navigation.service";
import {FormsModule} from "@angular/forms";
import * as M from 'materialize-css/dist/js/materialize';
import { LoginComponent } from './login/login.component';
import {ApplyTokenInterceptor} from "./shared/apply-token.interceptor";
import {RefreshTokenInterceptor} from "./shared/refresh-token.interceptor";
import { AttachmentsComponent } from './attachments/attachments.component';
import { TagsComponent } from './tags/tags.component';

@NgModule({
  declarations: [
    AppComponent,
    EditorComponent,
    HeaderComponent,
    NavigationComponent,
    LoginComponent,
    AttachmentsComponent,
    TagsComponent
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
    {provide:'M', useValue:M},
    {
      provide: HTTP_INTERCEPTORS,
      useClass: ApplyTokenInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: RefreshTokenInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}
