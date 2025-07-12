import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class GetImgService {

  constructor(private http: HttpClient) { }

  public async loadAvatarUrl(user: any): Promise<string> {
    const imageUrl = this.getInfo('profile_image_url', user);

    if (imageUrl) {
      try {
        const response = await this.http.head(imageUrl, { observe: 'response' }).toPromise();
        if (response?.status === 200) {
          return imageUrl; // Update avatar URL if image is valid
        }
      } catch (error) {
        console.error('Failed to load image:', error);
        this.handleAvatarError(error);
      }
    }

    return './assets/img/avatars/10.jpg'; // Default avatar URL
  }

  private getInfo(property: string, data: any): any {
    return data ? data.fields[property] : undefined;
  }

  private handleAvatarError(error: any): void {
    console.error('Avatar loading error:', error);
    // Handle avatar loading error here (e.g., set default avatar URL)
  }
}
