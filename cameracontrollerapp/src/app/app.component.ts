import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { camera } from './camera';
import { cameraService } from './camera.service';
import { controller } from './controller';
import { controllerService } from './controller.service';
import { FormGroup, NgForm } from '@angular/forms';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  title = 'cameracontrollerapp';
  public controllers!: controller[];
  public newCameras!: camera[];
  public cameras!: camera[];
  public editController!: controller;
  public deleteController!: controller;
  selectedRowIds: Set<number> = new Set<number>();
  private regForm!: FormGroup;
  updateController!: controller;
  constructor(private controllerService: controllerService, private cameraService: cameraService){}

ngOnInit(): void {
  this.getControllers();
  this.getCameras();
}
  public getControllers():void{
    this.controllerService.getControllers().subscribe(
      (response: controller[]) => {
        this.controllers = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }
  public getCameras():void{
    this.cameraService.getCameras().subscribe(
      (response: camera[]) => {
        this.cameras = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public onAddController(addForm: NgForm): void {

    //document.getElementById('add-controller-form').click();
    var controller = addForm.form.value;
    controller.camList=this.getSelectedRows();

    this.controllerService.addController(controller).subscribe(
      (response: controller) => {
        console.log(response);
        this.getControllers();
        addForm.reset();
      },
      (error: HttpErrorResponse) => {
        alert(error.error.message);
        addForm.reset();
      }
    );
  }

  public onUpdateController(addForm: NgForm, controllerId: number): void {
    
    var controller = addForm.form.value;
    controller.camList=this.getSelectedRows();

    this.controllerService.updateController(controller, this.updateController.uid).subscribe(
      (response: controller) => {
        console.log(response);
        this.getControllers();
      },
      (error: HttpErrorResponse) => {
        alert(error.error.message);
      }
    );
  }

  public onDeleteController(controllerId: number): void {
    if(confirm("Are you sure you want to delete this controller?")==true)
    {
      this.controllerService.deleteController(controllerId).subscribe(
        (response: void) => {
          console.log(response);
          this.getControllers();
        },
        (error: HttpErrorResponse) => {
          alert(error.message);
        }
      );
    }
    
  }

  public searchControllers(key: string): void {
    console.log(key);
    const results: controller[] = [];
    for (const controller of this.controllers) {
      if (controller.name.toLowerCase().indexOf(key.toLowerCase()) !== -1
      || controller.serialNum.toLowerCase().indexOf(key.toLowerCase()) !== -1
      || controller.ip.toLowerCase().indexOf(key.toLowerCase())) {
        results.push(controller);
      }
    }
    this.controllers = results;
    if (results.length === 0 || !key) {
      this.getControllers();
    }
  }

  public onOpenModal(controller: any | null, mode: string): void {
    debugger

    const container = document.getElementById('main-container');
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    
    if(mode === 'add'){
      button.setAttribute('data-target', '#addControllerModal');
    }
    
    if(mode === 'edit'){
      this.updateController = controller;
      button.setAttribute('data-target', '#editControllerModal');
    }
    
    if(mode === 'delete'){
      button.setAttribute('data-target', '#deleteControllerModal');
    }
    
    container?.appendChild(button);
    button.click();
  }
  
  onRowClick(id: number) {
    if(this.selectedRowIds.has(id)) {
     this.selectedRowIds.delete(id);
    }
    else {
      this.selectedRowIds.add(id);
    }
  }

  rowIsSelected(id: number) {
    return this.selectedRowIds.has(id);
  }

  getSelectedRows(){
    return this.cameras.filter(x => this.selectedRowIds.has(x.uid));
  }

  onLogClick() {
    console.log(this.getSelectedRows());
  }
}
