import { Directive, Input, OnInit, OnDestroy } from '@angular/core';
import * as L from 'leaflet';

export interface CustomMarkerOptions extends L.MarkerOptions {
  lat: number;
  lng: number;
}

@Directive({
  selector: '[leafletMarker]',
})
export class LeafletMarkerDirective implements OnInit, OnDestroy {
  @Input() leafletMarkerOptions: CustomMarkerOptions | undefined;

  public marker: L.Marker | undefined;

  ngOnInit() {
    if (this.leafletMarkerOptions && 'lat' in this.leafletMarkerOptions && 'lng' in this.leafletMarkerOptions) {
      this.marker = L.marker([this.leafletMarkerOptions.lat, this.leafletMarkerOptions.lng], this.leafletMarkerOptions);
      console.log('marker',  this.marker);

    } else {
      console.log('Invalid leafletMarkerOptions. Missing lat or lng properties.');
    }
  }

  ngOnDestroy() {
    if (this.marker) {
      this.marker.remove();
    }
  }
 
}
