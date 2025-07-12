import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-dropdown-menu',
  templateUrl: './dropdown-menu.component.html',
  styleUrl: './dropdown-menu.component.scss'
})
export class DropdownMenuComponent {

  @Input() categories: any[] = [];
  selectedCategoryOption: any;
  hoveredCategory: any;
  initialCategoriesData: any = this.categories;

  getCategoryChildren(category: any) {
    return category.children;
  }
 
  
  onMouseEnter(category: any): void {
    this.hoveredCategory = category;
  }

  onMouseLeave(): void {
    this.hoveredCategory = null;
  }
  onCategorySelect(event: any, category: any): void {
    console.log('Category selected:', category, 'Event:', event);
    this.selectedCategoryOption = event.value;
    this.applyFilters();
  }
  applyFilters(): void {
    console.log('******************************************');
    this.categories = [...this.initialCategoriesData];  
  console.log('Selected Category Option:', this.selectedCategoryOption);
  this.categories = this.categories.filter((item: any) => {
    const name = item.fields.name;
  const categoryMatches = this.selectedCategoryOption && name && typeof name === 'string' ?
  name.toLowerCase() == this.selectedCategoryOption.toLowerCase() : true; 
    return categoryMatches;
});

  }

}
