import { HttpClient } from '@angular/common/http';
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from 'src/environments/environment';
import { camera } from './camera';


@Injectable({
    providedIn: 'root'
})
export class cameraService {
    private apiServerUrl = environment.apiBaseUrl;

    constructor(private http: HttpClient) {
        
    }

    public getCameras(): Observable<camera[]> {
        return this.http.get<camera[]>(`${this.apiServerUrl}/camera/all`);
    }

    public addCamera(camera: camera): Observable<camera> {
        return this.http.post<camera>(`${this.apiServerUrl}/camera/add`, camera);
    }

    public updateCamera(camera: camera): Observable<camera> {
        return this.http.put<camera>(`${this.apiServerUrl}/camera/update`, camera);
    }

    public deleteCamera(CameraUid: number): Observable<void> {
        return this.http.delete<void>(`${this.apiServerUrl}/camera/delete/${CameraUid}`);
    }
}
