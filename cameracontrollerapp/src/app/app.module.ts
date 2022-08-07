import { HttpClient, HttpClientModule, HttpHandler } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { CommonModule } from '@angular/common';
import { FormsModule }   from '@angular/forms';
import { AppComponent } from './app.component';
import { controllerService } from './controller.service';
import { cameraService } from './camera.service';

@NgModule({
  declarations: [
    AppComponent
    
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    CommonModule,
    FormsModule
    
  ],
  providers: [controllerService,cameraService],
  bootstrap: [AppComponent]
})
export class AppModule { }
