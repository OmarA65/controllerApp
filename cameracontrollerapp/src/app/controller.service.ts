import { HttpClient } from '@angular/common/http';
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from 'src/environments/environment';
import { controller } from "./controller";


@Injectable({
    providedIn: 'root'
})
export class controllerService{
    private apiServerUrl = environment.apiBaseUrl;

    constructor(private http: HttpClient){

    }

    public getControllers(): Observable<controller[]>{
        return this.http.get<controller[]>(`${this.apiServerUrl}/controller/all`);
    }

    public addController(controller: controller): Observable<controller>{
        return this.http.post<controller>(`${this.apiServerUrl}/controller/add`, controller);
    }

    public updateController(controller: controller, controllerId: number): Observable<controller>{
        return this.http.put<controller>(`${this.apiServerUrl}/controller/update/${controllerId}`, controller);
    }

    public deleteController(controllerId: number): Observable<void>{
        return this.http.delete<void>(`${this.apiServerUrl}/controller/delete/${controllerId}`);
    }
}