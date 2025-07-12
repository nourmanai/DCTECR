import { ChangeDetectorRef, Component, OnInit,ViewChild, ElementRef,AfterViewInit, SimpleChanges, Input, OnChanges, DoCheck} from '@angular/core';
import { cibTwitter, cibLinkedin, cibYoutube, cibFacebook, cibInstagram,cilArrowThickBottom,cilWallet,cilStar,cilThumbUp,cilThumbDown,cilHandshake, cilOptions, cilCheckCircle,cilMediaRecord, cilGlobeAlt, cilListNumbered,
  cilPaperPlane, cilBellExclamation, cilBookmark, cilCalendar, cilCalendarCheck, cilUser, cilWarning, cifUs, cifAd, cifBr, cifBs,
   cifBa, cifCa, cifBb, cifCd, cifAe, cifCh, cifCi, cifBj, cifCl, cifCm, cifCo, cifCr, cifAt, cifCu, cifCv, cifBw, cifCy, cifCz, 
   cifDe, cifDj, cifDk, cifDm, cifDo, cifDz, cifEc, cifEe, cifEg, cifEr, cifEs, cifEt, cifFi, cifFj, cifFm, cifFr, cifId, cifIe, 
   cifIl, cifIn, cifIq, cifIr, cifIs, cifIt, cifJm, cifJo, cifJp, cifKe, cifKg, cifKh, cifKi, cifKm, cifKn, cifKp, cifKr, cifKw,
    cifKz, cifGa, cifGb, cifGd, cifGe, cifGh, cifGm, cifGn, cifGq, cifGr, cifGt, cifGw, cifGy, cifHk, cifHn, cifHr, cifHt, cifHu,
  cifLa, cifLb, cifLc, cifLi, cifLk, cifLr, cifLs, cifLt, cifLu, cifLv, cifLy, cifMa, cifMc, cifMd, cifMe, cifMg, cifMh, cifMk,
  cifMl, cifMm, cifMn, cifMr, cifMt, cifMu, cifMv, cifMw, cifMx, cifMy, cifMz, cifNa, cifNe, cifNg, cifNi, cifNl, cifNo, cifNp,
   cifNr, cifNu, cifNz, cifOm, cifPa, cifPe, cifPg, cifPh, cifPk, cifPl, cifPt, cifPw, cifPy, cifQa, cifRo, cifRs, cifRu, cifRw, 
   cifSa, cifSb, cifSc, cifSd, cifSe, cifSg, cifSi, cifSk, cifSl, cifSm, cifSn, cifSo, cifSr, cifSs, cifSt, cifSv, cifSy, cifSz,
   cifTd, cifTg, cifTh, cifTj, cifTl, cifTm, cifTn, cifTo, cifTr, cifTt, cifTv, cifTw, cifTz, cifUa, cifUg, cifUy, cifUz, cifVa,
   cifVc, cifVe, cifVn, cifWs, cifXk, cifYe, cifZa, cifZm, cifZw, cifAf, cifAg, cifAl, cifAm, cifAo, cifAr, cifAu, cifAz, cifBd,
   cifBe, cifBf, cifBg, cifBh, cifBi, cifBn, cifBo, cifBt, cifBy, cifBz, cifCf, cifCg, cifCn, cibCampaignMonitor, cilMonitor, 
   cilChartPie, cilFolderOpen,cilReload,cilArrowBottom,cilArrowTop,cilMinus,cilFrown,cilHappy,cilMeh,cilSearch,cilHeart,cilCart,
   cilCash,
   cilList,
   cilTag,
   cilPuzzle,
   cilListFilter,
   cilFilterX
  } from '@coreui/icons';
   import { HttpClient, HttpHeaders } from '@angular/common/http';
   import { IconSetService } from '@coreui/icons-angular';
   import { GlobalService } from '../GlobalService';
   import { Observable, catchError, forkJoin, map, throwError } from 'rxjs';
   import { DomSanitizer } from '@angular/platform-browser';
import { filter } from 'rxjs/operators';
import { Chart, ChartOptions, LegendItem } from 'chart.js';
import { MapOptions, TileLayerOptions, GeoJSONOptions } from 'leaflet';
import * as L from "leaflet";
import * as unorm from 'unorm';
import * as d3 from 'd3';

 

@Component({
  selector: 'app-sales',
  templateUrl: './sales.component.html',
  styleUrl: './sales.component.scss'
})
export class SalesComponent implements OnInit,DoCheck   {

  @Input() ReviewOrdersArray: any[] = [];

  finalProductsReviews: any[] =[];
selectedavailabilityOption: any ="";
  initialProductsReviewsData: any[] =[];
typical_price_rangeSelectedOption: any ="";
  typical_price_rangeOptions: any[] =[];
priceRangeSearchTerm: any;
  initialtypical_price_rangeOptions: any[]= [];
discountPercentageOption: any = null;
selectedRateOption: any = null;
  supplierOptions: any[] =[];
SupplierSelectedOption: any ="";
  cheapestProduct: any;
  mostExpensiveProduct: any;


  @ViewChild('productContainer') productContainer: ElementRef | undefined;

  allOrdersData: any[]=[];
  initialOrdersData: any[]=[];
  allSalesData: any[]=[];
  initialSalesData: any[]=[];
  allShipmentsData: any[]=[];
  initialShipmentsData: any[]=[];
  allProductSKUData: any[]=[];
  initialProductSKUData: any[]=[];
  allSuppliersData: any[]=[];
  initialSuppliersData: any[]=[];
  allCategoriesData: any[]=[];
  initialCategoriesData: any[]=[];
  allReviewsData: any[]=[];
  initialReviewsData: any[]=[];
  finalProductSKUs: any[]=[];
  finalSales: any[]=[];
  finalReviews: any[]=[];
  allAttributeValuesData: any[]=[];
  initialAttributeValuesData: any[]=[];
  allAttributesData:any[]=[];
  initialAttributesData: any[]=[];
  allClientsData:any[]=[];
  initialClientsData:any[]=[];
  finalAttributeValues: any[]=[];
  allProductsData: any[]=[];
  initialProductsData: any[]=[];
  finalOrders: any[]=[];
  finalCategories: any[]=[];
  finalProducts: any[]=[];
   Ranking : any[] =[];
   readonlyRate: boolean = true; 
   showFilters: boolean =false;
   searchText = '';
   clickedreview : any = null ; 
   clickedOrder : any = null;
  currentPosition : number = 0;
  intervalId: number =0;
  filteredCategories: any[] =[];
selectedCategoryOption: any;
CategoryOptions: any;
  categoryTree: any[]=[];
  hoveredCategory: any;
  selectedReviewSale: any;
  selectedReviewOrdersArray: any[]=[];
  itemsPerPageTeam = 5; 
  itemsPerPage = 5; 
  currentPage = 1;
  currentPageTeam = 1;
  UniqueShipmentTypes: any[]=[];
  ShipmentTypesCount: any[] =[];
  chartPieShipmentData: any; 
  chartLineShipmentData:any;
  selectedShipment: any;
  activeLinkIndex: number = -1;
  chartBarShipmentData:any;
  ShipmentLateDays: any;
  UniqueShipmentLocations: any[] =[];
  mapFilter: any;
  SupplierOption: any = null;
  showOrderFilters:boolean =false;
shippingtypeFilter: any ='';
shippingstatusFilter: any ='';
  uniqueShipmentstatus: any[]=[];


  constructor(public domSanitizer: DomSanitizer, public globalService: GlobalService, private http: HttpClient, private cdr: ChangeDetectorRef , public iconSet: IconSetService, )
   {
    iconSet.icons = {cibTwitter,cibLinkedin,cibYoutube,cibFacebook,cilFolderOpen, cibCampaignMonitor ,cibInstagram,cilCash,cilFilterX,cilList,cilListFilter,cilTag,cilPuzzle,cilCart,cilWallet,cilStar,cilThumbUp,cilThumbDown,cilHeart,cilHandshake,cilSearch,cilFrown,cilHappy,cilMeh,cilArrowThickBottom,cilMediaRecord,cilReload,cilArrowBottom,cilArrowTop,cilMinus,cilMonitor,cilChartPie,cilOptions,cilCheckCircle, cilGlobeAlt, cilListNumbered, cilPaperPlane, cilBellExclamation, cilBookmark, cilCalendar , cilCalendarCheck, cilUser, cilWarning, cifUs, cifAd, cifBr, cifBs, cifBa, cifCa, cifBb,cifCd, cifAe,cifCh, cifCi, cifBj, cifCl,cifCm,cifCo,cifCr,cifAt,cifCu,cifCv, cifBw,cifCy,cifCz,cifDe,cifDj,cifDk,cifDm,cifDo,cifDz,cifEc,cifEe,cifEg,cifEr,cifEs,cifEt,cifFi,cifFj,cifFm,cifFr,cifId,cifIe,cifIl,cifIn,cifIq,cifIr,cifIs,cifIt,cifJm,cifJo,cifJp,cifKe,cifKg,cifKh,cifKi,cifKm,cifKn,cifKp,cifKr,cifKw,cifKz,cifGa,cifGb,cifGd,cifGe,cifGh,cifGm,cifGn,cifGq,cifGr,cifGt,cifGw,cifGy,cifHk,cifHn,cifHr,cifHt,cifHu,cifLa,cifLb,cifLc,cifLi,cifLk,cifLr,cifLs,cifLt, cifLu,cifLv,cifLy,cifMa,cifMc,cifMd,cifMe,cifMg,cifMh,cifMk,cifMl,cifMm,cifMn,cifMr,cifMt,cifMu,cifMv,cifMw,cifMx,cifMy,cifMz,cifNa,cifNe,cifNg,cifNi,cifNl,cifNo,cifNp,cifNr,cifNu,cifNz,cifOm,cifPa,cifPe,cifPg,cifPh,cifPk,cifPl,cifPt,cifPw,cifPy,cifQa,cifRo,cifRs,cifRu,cifRw,cifSa,cifSb,cifSc,cifSd,cifSe,cifSg,cifSi,cifSk,cifSl,cifSm,cifSn,cifSo,cifSr,cifSs,cifSt,cifSv,cifSy,cifSz,cifTd,cifTg,cifTh,cifTj,cifTl,cifTm,cifTn,cifTo,cifTr,cifTt,cifTv,cifTw,cifTz,cifUa,cifUg,cifUy,cifUz,cifVa,cifVc,cifVe,cifVn,cifWs,cifXk,cifYe,cifZa,cifZm,cifZw,cifAf,cifAg,cifAl,cifAm,cifAo,cifAr,cifAu,cifAz,cifBd,cifBe,cifBf,cifBg,cifBh,cifBi,cifBn,cifBo,cifBt,cifBy,cifBz,cifCf,cifCg,cifCn  }; 

      
  }

 
   ngOnInit(): void {

 
    // Categories
    this.globalService.retrieveall('Category','Categories').subscribe((responseData: any) => {
     
      this.allCategoriesData = responseData;
      this.initialCategoriesData = responseData;
      console.log(" all Categories Data",this.allCategoriesData );
      this.returnFinalCategories();
         // Suppliers
    this.globalService.retrieveall('Supplier','Suppliers').subscribe((responseData: any) => {
     
      this.allSuppliersData = responseData;
      this.initialSuppliersData = responseData;
      console.log(" all Suppliers Data",this.allSuppliersData );
    },
    (error: any) => {
      console.log(error);
    }
    );  

       // Shipments
       this.globalService.retrieveall('Shipment','Shipments').subscribe((responseData: any) => {
     
        this.allShipmentsData = responseData;
        this.initialShipmentsData = responseData;
        console.log(" all Shipments Data",this.allShipmentsData );
      },
      (error: any) => {
        console.log(error);
      }
      );  

       // Client
        this.globalService.retrieveall('Clients','Clients').subscribe((responseData: any) => {
     
         this.allClientsData = responseData;
         this.initialClientsData = responseData;
                 console.log(" all Clients  Data",this.allClientsData );
              },
              (error: any) => {
                console.log(error);
              }
               );

                 // Product
      this.globalService.retrieveall('Product','Products').subscribe((responseData: any) => {
     
        this.allProductsData = responseData;
        this.initialProductsData = responseData;
         console.log(" all Products  Data",this.allProductsData );
         this. returnFinalProducts();

           // Attributes 
        this.globalService.retrieveall('Property','Properties').subscribe((responseData: any) => {
     
          this.allAttributesData = responseData;
          this.initialAttributesData = responseData;
           console.log(" all Attributes  Data",this.allAttributesData );

             // Attribute Values
      this.globalService.retrieveall('PropertyValue','PropertyValues').subscribe((responseData: any) => {
     
        this.allAttributeValuesData = responseData;
        this.initialAttributeValuesData = responseData;
         console.log(" all Attribute Values Data",this.allAttributeValuesData );
         this.returnFinalAttributeValues();
      },
      (error: any) => {
        console.log(error);
      }
       );
// ProductSKU
this.globalService.retrieveall('ProductSKU','ProductSKU').subscribe((responseData: any) => {
     
  this.allProductSKUData = responseData;
  this.initialProductSKUData = responseData;
  console.log(" all ProductSKU Data",this.allProductSKUData );
  this.returnFinalProductSKUs();
    // Reviews
    this.globalService.retrieveall('Review','Reviews').subscribe((responseData: any) => {
     
      this.allReviewsData = responseData;
      this.initialReviewsData = responseData;
      console.log(" all Reviews Data",this.allReviewsData );
      this.returnFinalReviews();
    },
    (error: any) => {
      console.log(error);
    }
    );
     // Orders
     this.globalService.retrieveall('Order','Orders').subscribe((responseData: any) => {
     
      this.allOrdersData = responseData;
      this.initialOrdersData = responseData;
      console.log(" all Orders Data",this.allOrdersData );
      this.returnFinalOrders();
      // Sales
    this.globalService.retrieveall('Sales','Sales').subscribe((responseData: any) => {
     
      this.allSalesData = responseData;
      this.initialSalesData = responseData;
      console.log(" all Sales Data",this.allSalesData );
      this.returnFinalSales();
    },
    (error: any) => {
      console.log(error);
    }
    );   
    },
    (error: any) => {
      console.log(error);
    }
    );   

},
(error: any) => {
  console.log(error);
}
); 

        },
        (error: any) => {
          console.log(error);
        }
         );
      },
      (error: any) => {
        console.log(error);
      }
       );


        this.categoryTree = this.globalService.getCategoryTree(this.finalCategories);
              console.log("categoryTree",this.categoryTree );
              console.log("children ",this.categoryTree[0].children );

        const categoryTreeElement = document.getElementById('category-tree');
        if (categoryTreeElement) {
          const categoryTreeHtml = this.globalService.displayCategoryTree(this.categoryTree);
          categoryTreeElement.innerHTML = categoryTreeHtml;
        }
    
    },
    (error: any) => {
      console.log(error);
    }
    );

  }




filtercategory(event: any) {
    this.filteredCategories = this.allCategoriesData;
   
  const searchTerm = event.target.value;
  this.filteredCategories = this.finalCategories.filter(category => category.fields.name.toLowerCase()==searchTerm.toLowerCase());
}
 
  returnFinalCategories(){
    const enrichedCategories: any[] = [];
    const categoryMap: { [id: string]: any } = {};
  
    this.allCategoriesData.forEach((category) => {
      categoryMap[category.id] = category;
    });
  
    for (let category of this.allCategoriesData) {
      let parent: any;
      const parentId = category.fields.parent_id;
  
      if (parentId === 'NaN' ||!parentId) {
        parent = null;
      } else {
        parent = categoryMap[parentId];
      }
  
        const enrichedCategory = {
         ...category,
          parent
        };
        enrichedCategories.push(enrichedCategory);
      
    }
    this.finalCategories = enrichedCategories;

    console.log("enrichedCategories", enrichedCategories);
    return enrichedCategories;
  }
  returnFinalProductSKUs(){
    const enrichedSKUs: any[] = [];
    const ProductMap: { [id: string]: any } = {};
    const AttributeMap: { [id: string]: any } = {};

    this.finalProducts.forEach((prod) => {
      ProductMap[prod.id] = prod;
    });
    this.allAttributesData.forEach((atr) => {
      AttributeMap[atr.id] = atr;
    });

    for (const sku of this.allProductSKUData )
      {
        let product ;
        let attribute;
        let finalAttributes: any[] = [];

        const prodId = sku.fields.product_key;
        const attributesId = sku.fields.attributes_key;

        product = ProductMap[prodId];

        for (const atrId of attributesId) { 

          attribute = AttributeMap[atrId];
          if (attribute) { 
            finalAttributes.push(attribute);
          } else {
            // console.log(`Order not found: ${atrId}`);
          }
        }
        const enrichedSKU = {
          ...sku,
          product,
          finalAttributes
         };
         enrichedSKUs.push(enrichedSKU);

      }
      this.finalProductSKUs = enrichedSKUs;
      console.log("enrichedSKUs", enrichedSKUs);
      return enrichedSKUs;
    
  }
  returnFinalOrders(){
    const enrichedOrders: any[] = [];

    const SKUMap: { [id: string]: any } = {};
    const ShipmentMap: { [id: string]: any } = {};
    const ClientMap: { [id: string]: any } = {};
    const SupplierMap: { [id: string]: any } = {};

    this.finalProductSKUs.forEach((atr) => {
      SKUMap[atr.id] = atr;
    });
    this.allShipmentsData.forEach((p) => {
      ShipmentMap[p.id] = p;
    });
    this.allClientsData.forEach((atr) => {
      ClientMap[atr.id] = atr;
    });
    this.allSuppliersData.forEach((p) => {
      SupplierMap[p.id] = p;
    });
    for (const order of this.allOrdersData )
      {
        let sku ;
        let shipment;
        let client ;
        let supplier;

        const skuId = order.fields.productSKU_key;
        sku = SKUMap[skuId];

        const shipId = order.fields.shipment_key;
        shipment = ShipmentMap[shipId];

        const cltId = order.fields.client_key;
        client = ClientMap[cltId];

        const supId = order.fields.supplier_key;
        supplier = SupplierMap[supId];

        const enrichedOrder = {
          ...order,
          sku,
          shipment,
          client,
          supplier
         };
         enrichedOrders.push(enrichedOrder);

      }
      this.finalOrders = enrichedOrders;
      console.log("enrichedOrders", enrichedOrders);
      this.supplierOptions = this.globalService.getUniqueOptions(this.finalOrders, "supplier", "name");
      return enrichedOrders;
    
  }
  returnFinalAttributeValues(){
    const enrichedAttributeValues: any[] = [];
    const AttributeMap: { [id: string]: any } = {};
    const ProductMap: { [id: string]: any } = {};

    this.allAttributesData.forEach((atr) => {
      AttributeMap[atr.id] = atr;
    });
    this.finalProducts.forEach((p) => {
      ProductMap[p.id] = p;
    });
    for (const val of this.allAttributeValuesData )
      {
        let attribute ;
        let product;
        const atrId = val.fields.attribute_key;
        attribute = AttributeMap[atrId];

        const productId = val.fields.product_key;
        product = ProductMap[productId];

        const enrichedAttributeValue = {
          ...val,
          attribute,
          product
         };
         enrichedAttributeValues.push(enrichedAttributeValue);

      }
      this.finalAttributeValues = enrichedAttributeValues;
      console.log("enrichedAttributeValues", enrichedAttributeValues);
      return enrichedAttributeValues;
    
    
  }
  returnFinalReviews(){
    const enrichedReviews: any[] = [];
    const SKUMap: { [id: string]: any } = {};
    this.finalProductSKUs.forEach((sku) => {
      SKUMap[sku.id] = sku;
    });
    for (const review of this.allReviewsData )
      {
        let productSKU ;
        const skuId = review.fields.productSKU_key;
        productSKU = SKUMap[skuId];
        const enrichedReview = {
          ...review,
          productSKU
         };
         enrichedReviews.push(enrichedReview);

      }
      this.finalReviews = enrichedReviews;
      this.finalProductsReviews = this.finalReviews.filter(review => review.productSKU);
      this.typical_price_rangeOptions = this.globalService.getUniquePriceRangeValues( this.finalProductsReviews); 
      this.initialtypical_price_rangeOptions = this.globalService.getUniquePriceRangeValues( this.finalProductsReviews); 

      this.initialProductsReviewsData =  this.finalReviews.filter(review => review.productSKU);
      this.cheapestProduct = this.findPriceExtremes().cheapest;
      this.mostExpensiveProduct = this.findPriceExtremes().mostExpensive;


      console.log("enrichedReviews", enrichedReviews);
      this.getTop10RankedProducts();
      return enrichedReviews;
    
  }
  returnFinalSales(){
    const enrichedSales: any[] = [];
    const SKUMap: { [id: string]: any } = {};
    const OrderMap: { [id: string]: any } = {};

    this.finalProductSKUs.forEach((sku) => {
      SKUMap[sku.id] = sku;
    });
    this.finalOrders.forEach((order) => {
      OrderMap[order.id] = order;
    });

    for (const sale of this.allSalesData )
      {
        let productSKU ;
        let order;
        let finalOrders: any[] = [];
        const skuId = sale.fields.productSKU_key;
        const ordersId = sale.fields.orders_key;
        productSKU = SKUMap[skuId];

        for (const orderId of ordersId) {
           order = OrderMap[orderId];
          if (order) { 
            finalOrders.push(order);
          } else {
            // console.log(`Order not found: ${orderId}`);
          }
        }
        const enrichedSale = {
          ...sale,
          productSKU,
          finalOrders
         };
         enrichedSales.push(enrichedSale);

      }
      this.finalSales = enrichedSales;
      console.log("enrichedSales", enrichedSales);
      return enrichedSales;

  }

 returnFinalProducts(){
  const enrichedProducts: any[] = [];
  const categoryMap: { [id: string]: any } = {};
  
  this.finalCategories.forEach((category) => {
    categoryMap[category.id] = category;
  });

  for (const product of this.allProductsData )
    {
      let category;
      const catId = product.fields.category_id;
      category = categoryMap[catId];
      const enrichedProduct = {
        ...product,
        category
       };
       enrichedProducts.push(enrichedProduct);

    }
    this.finalProducts= enrichedProducts;

    console.log("enrichedProducts", enrichedProducts);
    return enrichedProducts;
 }
 
 

  getTop10RankedProducts() {
    this.Ranking = this.finalReviews.sort((a, b) => b.fields.rating - a.fields.rating).slice(0, 10);
    this.displayProduct();
}


  displayProduct(){

    let carousel: HTMLElement | null = document.querySelector('.carousel');
      let productContainer: HTMLElement | null = document.querySelector('.product-container');
      

      if (carousel && productContainer) {


    let productList = this.Ranking;
        let productListLength = productList.length;
        console.log("productList : ",productList,"  productListLength : ", productListLength );


        const animateCarousel = () => {
      if (carousel) {
       carousel.style.transform = `translateX(${this.currentPosition}px)`;
      }
    }

    const startAnimation = () => {
      this.intervalId = setInterval(() => {
        this. currentPosition -= 100; 
        if (this.currentPosition <= -productListLength * 350 -50 ) { 
          this.currentPosition = 0;
        }
        animateCarousel();
      }, 4000); 
    }

    const stopAnimation = () => {
      clearInterval(this.intervalId);
    }

    document.getElementById('prevButton')?.addEventListener('click', () => {
      stopAnimation();
      this.currentPosition += 100; 
      animateCarousel();
      startAnimation();
    });

    document.getElementById('nextButton')?.addEventListener('click', () => {
      stopAnimation();
      this.currentPosition -= 100; 
      animateCarousel();
      startAnimation();
    });
    startAnimation();
  }
}
 


public displayCategoryTree(categoryTree: any[]): string {
  let html = '';
  for (const category of categoryTree) {
    html += `<mat-option [value]="${category}">${category.fields.name}</mat-option>`;
    if (category.children && category.children.length > 0) {
      html += this.displayCategoryTree(category.children);
    }
  }
  return html;
}

public onCategorySelect(event: any) {
  const selectedCategory = event.value;
  const children = this.getCategoryChildren(selectedCategory);
  if (children && children.length > 0) {
    const dropdownHtml = this.displayCategoryTree(children);
    const dropdownElement = document.getElementById('subcategory-dropdown');
    if (dropdownElement) {
      dropdownElement.innerHTML = dropdownHtml;
    }
  }
}

public getCategoryChildren( parent: any): any[] {
  return parent.children;
}


filterPriceRanges() : void {
 this.typical_price_rangeOptions = [...this.initialtypical_price_rangeOptions];  
 this.typical_price_rangeOptions = this.typical_price_rangeOptions.filter((item: any) => {

  let optionMatches :any; 
  if (Array.isArray(item) && item.length === 2) {
    optionMatches = this.priceRangeSearchTerm   ?    
    item[0].includes(this.priceRangeSearchTerm)||item[1].includes(this.priceRangeSearchTerm):
    true;
  }
   else { 
    optionMatches = this.priceRangeSearchTerm   ?    
    item.includes(this.priceRangeSearchTerm):
    true;
  }

  return optionMatches;
});
}


resetDiscountFilter() {
  this.discountPercentageOption = null;
  this.applyFilters();
}

resetRateFilter() {
  this.selectedRateOption = null;
  this.applyFilters();
}
onRateChange(): void {
  this.applyFilters();
}


applyFilters(): void {
  this.finalProductsReviews = [...this.initialProductsReviewsData];

  this.finalProductsReviews = this.finalProductsReviews.filter((item: any) => {
    const nbr_unit_inventory = this.globalService.getInfo('nbr_unit_inventory', item.productSKU);
    const typical_price_range = this.globalService.getInfo('typical_price_range', item.productSKU);
    const discount = this.globalService.extractDiscountPercentage(this.globalService.getInfo('discount_percentage', item.productSKU));
    const rate = Math.round(this.globalService.getInfo('rating', item));
    const supplier = this.globalService.getItemByObject("sku",item.productSKU, this.finalOrders).supplier.fields.name;
    const name = this.globalService.getInfo('product_title', item.productSKU.product);


    //searchText
    const nbr_unit_inventoryMatches = this.selectedavailabilityOption 
      ? this.globalService.getStockStatus(nbr_unit_inventory) === this.selectedavailabilityOption
      : true;

    const typical_price_rangeMatches = this.typical_price_rangeSelectedOption 
      ? typical_price_range === this.typical_price_rangeSelectedOption
      : true;

    const discountMatches = this.discountPercentageOption !== null 
      ? discount === this.discountPercentageOption
      : true;

    const rateMatches = this.selectedRateOption !== null 
      ? rate === this.selectedRateOption
      : true;

    const supplierMatches = this.SupplierSelectedOption !== ""  
    ? supplier === this.SupplierSelectedOption 
    : true;

    const nameMatches = this.searchText!=='' && name ? name.toLowerCase().includes(this.searchText.toLowerCase()) : true ; 

    return nbr_unit_inventoryMatches && typical_price_rangeMatches && discountMatches && rateMatches && supplierMatches && nameMatches;
  });
}

toggleSupplierOption(value: boolean | null) {
  this.SupplierOption = value;
  this.applyOrderFilters();
}

applyOrderFilters():void{
  this.selectedReviewOrdersArray = [...this.selectedReviewSale.finalOrders];

  this.selectedReviewOrdersArray = this.selectedReviewOrdersArray.filter((item: any) => {

 
      const isSupplier = this.globalService.getInfo('isSupplier', item.shipment);
      const shipmentType = this.globalService.getInfo('type', item.shipment);
      const shipmentStatus = this.globalService.getInfo('status', item.shipment);

      // const isSupplierMatches = this.SupplierOption!== null && isSupplier? this.SupplierOption === isSupplier : true ;
      const isSupplierMatches = this.SupplierOption === null || this.SupplierOption === isSupplier;

      
      const ShipmentStatusMatches = this.shippingstatusFilter!=='' && shipmentStatus ? shipmentStatus.toLowerCase() === this.shippingstatusFilter.toLowerCase() : true ;

    const ShipmentTypeMatches = this.shippingtypeFilter!=='' && shipmentType ? shipmentType.toLowerCase() === this.shippingtypeFilter.toLowerCase() : true ;


    return isSupplierMatches  && ShipmentTypeMatches && ShipmentStatusMatches;
  });

}


SelectedCategory(event: any, category: any): void {
  console.log('Category selected:', category, 'Event:', event);
  this.selectedCategoryOption = event.value;
}
onMouseEnter(category: any): void {
  this.hoveredCategory = category;
}

onMouseLeave(): void {
  this.hoveredCategory = null;
}


toggleFilters(): void {
  this.showFilters = !this.showFilters;
}

toggleOrderFilters():void{
  this.showOrderFilters = !this.showOrderFilters;

}

pageChanged(event: any): void {
this.currentPage = event.page;
}


parsePrice(price: string): number {
  if (price.includes('$')) {
    return parseFloat(price.replace('$', '').replace(',', ''));
  } else if (price.includes('₹')) {
    const priceInINR = parseFloat(price.replace('₹', '').replace(',', ''));
    return this.globalService.convertMoney(priceInINR,0.012);
  } else {
    throw new Error('Unsupported currency');
  }
}

findPriceExtremes(): { cheapest: any; mostExpensive: any } {
  if (this.finalProductsReviews.length === 0) {
    throw new Error('No products available');
  }

  let cheapestProduct = this.finalProductsReviews[0].productSKU;
  let mostExpensiveProduct = this.finalProductsReviews[0].productSKU;
  let minPrice = this.parsePrice(this.finalProductsReviews[0].productSKU.fields.discounted_price);
  let maxPrice = this.parsePrice(this.finalProductsReviews[0].productSKU.fields.discounted_price);

  for (const review of this.finalProductsReviews) {
    const price = this.parsePrice(review.productSKU.fields.discounted_price);
    if (price < minPrice) {
      minPrice = price;
      cheapestProduct = review.productSKU;
    }
    if (price > maxPrice) {
      maxPrice = price;
      mostExpensiveProduct = review.productSKU;
    }
  }

  return { cheapest: cheapestProduct, mostExpensive: mostExpensiveProduct };
}

LateDaysByType(data: any[]): { [key: string]: number } {
  const typeSums: { [key: string]: number } = {};
  const typeCounts: { [key: string]: number } = {};

  data.forEach(order => {
    const type = this.globalService.getInfo('type', order.shipment);
    const lateDays = this.globalService.getInfo('late_days', order.shipment);

    if (!typeSums[type]) {
      typeSums[type] = 0;
      typeCounts[type] = 0;
    }

    typeSums[type] += lateDays;
    typeCounts[type] += 1;
  });

  const typeAverages: { [key: string]: number } = {};

  for (const type in typeSums) {
    typeAverages[type] = Math.round(typeSums[type] / typeCounts[type]);
  }

  return typeAverages;
}


showProductDetails(review : any) {

//  const supplier = this.globalService.getItemByObject("sku",review.productSKU, this.finalOrders).supplier
//  const shipment = this.globalService.getItemByObject("shipment",review.productSKU, this.finalOrders).shipment
//  const client  = this.globalService.getItemByObject("client",review.productSKU, this.finalOrders).client

  this.selectedReviewSale = this.globalService.getItemByObject("productSKU",review.productSKU, this.finalSales)
  this.selectedReviewOrdersArray = this.selectedReviewSale.finalOrders;
  

  this.UniqueShipmentTypes = this.globalService.getUniqueOptions(this.selectedReviewOrdersArray,'shipment','type');
  this.UniqueShipmentLocations= this.globalService.getUniqueOptions(this.selectedReviewOrdersArray,'shipment','country');
this.uniqueShipmentstatus = this.globalService.getUniqueOptions(this.selectedReviewOrdersArray,'shipment','status');
  this.ShipmentTypesCount =this.countShipmentTypes(this.selectedReviewOrdersArray);
  this.ShipmentLateDays= this.LateDaysByType(this.selectedReviewOrdersArray);


this.chartPieShipmentData = {
  labels: this.UniqueShipmentTypes, 
  datasets: [
    {
      data: this.ShipmentTypesCount,
      backgroundColor: this.globalService.getArrayofRandomColors(this.UniqueShipmentTypes.length),
      hoverBackgroundColor:this.globalService.getArrayofRandomColors(this.UniqueShipmentTypes.length)
    }
  ]
};

this.chartLineShipmentData = {
  labels: this.UniqueShipmentTypes,    
  datasets: [
    {
      label: 'Shipping Types Count',
      backgroundColor: 'rgba(220, 220, 220, 0.2)',
      borderColor: 'rgba(220, 220, 220, 1)',
      pointBackgroundColor: 'rgba(255, 99, 132, 1)',
      pointBorderColor: '#fff',
      data: this.ShipmentTypesCount,
      tooltip: {
        enabled: true,
        callbacks: {
          label: (tooltipItem: any) => {
           return tooltipItem.formattedValue;
           },
          
        },      
      },
    }
  ],

};

const lateDays: number[] = Object.values(this.ShipmentLateDays) as number[];
const colorMap = this.globalService.getConsistentColorMap(lateDays);
const colors = lateDays.map(days => colorMap[days]);


this.chartBarShipmentData = {
  labels: this.UniqueShipmentTypes,

  datasets: [
    {
      label: 'Average Late Days',
      yAxisLabel: 'Average',
      backgroundColor: colors,
      borderColor: colors.map((color: string) => color.replace('0.2', '1')),
      borderWidth: 2,
      data: this.ShipmentLateDays,
      config: {
        animated: false,
      },
    },
  ],
 
};
}


chartBarOptions: ChartOptions<'bar'> = {
  maintainAspectRatio: false,
  animation: {
    duration: 0,
  },
  responsive: true,
  scales: {
    y: {
      ticks: {
        stepSize: 1, 
      },
      beginAtZero: true,
      title: {
        display: true,
        text: 'Average Late Days',  
      },
    },
  },
  plugins: {
    legend: {
      labels: {
        
        generateLabels: (chart: Chart<'bar'>): LegendItem[] => {
          const dataset = chart.data.datasets[0];
          const backgroundColor = dataset.backgroundColor as string[];
        
          const data: number[] = Object.values(dataset.data) as number[];

          const colorMap: Map<string, number[]> = new Map();
        
          backgroundColor.forEach((color, index) => {
            const yValue = data[index];
            if (color && yValue !== undefined) {

              if (!colorMap.has(color)) {
                colorMap.set(color, []);
              }
              colorMap.get(color)!.push(yValue);
            }
          });
        
          return Array.from(colorMap.entries()).map(([color, yValues]) => {
            const maxYValue = Math.max(...yValues);
            return {
              text: `${maxYValue} days Late`,  
              fillStyle: color,
              strokeStyle: color,
              lineWidth: 2,
            };
          });
        },
        
      },
    },
  },
};

chartLineShipmentOptions = {

  maintainAspectRatio: false,
  scales: {
    y: {
      beginAtZero: true,
      title: {
        display: true,
        text: 'Count',
      },
      ticks: {
        callback: function(value: any, index: any, values: any) {
          return value;
        },
      },
    },
  },
  plugins: {
    tooltip: {
      callbacks: {
        label: function(context: { parsed: { y: number; }; }) {
          return context.parsed.y + '';
        },
      },
    },
  },
};


OrderDetails(order : any){}

showShipmentDetails(shipment: any, index: number): void {
  this.selectedShipment = shipment;
  this.activeLinkIndex = index;
}

// Function to count occurrences of each type
countShipmentTypes(data: any[]): number[] {
  const types = data.map(order => this.globalService.getInfo('type', order.shipment));
  const typeCounts = types.reduce((counts, type) => {
    counts[type] = (counts[type] || 0) + 1;
    return counts;
  }, {});

  return Object.values(typeCounts);
}




chartPieShipmentOptions = {
  aspectRatio: 1,
  responsive: true,
  maintainAspectRatio: false,
  radius: '100%'
};




// start map 
locationMap: { [location: string]: { Orders_count: number; Total: number; price:string;  [key: string]: any;}  } = {};

jsonUrl = './assets/files/countries.json';

options: MapOptions = {
  center: [52.3, 8.0],
  zoom: 3,
  scrollWheelZoom: false,
  zoomControl: true,  
};


getRandomColor(): string {
  const colors = [
  'red', 'orange', 'yellow', 'green', 'blue', 'purple', 'pink', 'brown', 'teal', 'cyan',
  'maroon', 'navy', 'olive', 'lime', 'indigo', 'magenta', 'peach', 'turquoise', 'lavender'
 ];
  const randomIndex = Math.floor(Math.random() * colors.length);
  return colors[randomIndex];
 }

private previousSelectedReviewOrdersArray: any[] = [];
private map: L.Map | undefined;
private geoJsonLayer: L.GeoJSON | undefined;
private legendControl: L.Control | undefined;

ngDoCheck(): void {
  if (this.hasSelectedReviewOrdersArrayChanged()) {
    this.updateLocationMap();
    this.previousSelectedReviewOrdersArray = [...this.selectedReviewOrdersArray];
  }
}

private hasSelectedReviewOrdersArrayChanged(): boolean {
  return this.selectedReviewOrdersArray.length !== this.previousSelectedReviewOrdersArray.length ||
    this.selectedReviewOrdersArray.some((order, index) => order !== this.previousSelectedReviewOrdersArray[index]);
}

updateLocationMap(): void {
  this.locationMap = {};

  this.selectedReviewOrdersArray.forEach((order: any) => {
    const location = order.shipment.fields.country;
    const shipment_price = order.shipment.fields.shipment_price;

    if (!this.locationMap[location]) {
      this.locationMap[location] = { Orders_count: 0, Total: 0 , price : '' };
    }

    this.locationMap[location].Orders_count += 1;
    this.locationMap[location].Total += shipment_price;
    this.locationMap[location].price = shipment_price.toString() +' '+ order.shipment.fields.currency;

  });

  console.log("locationMap", this.locationMap);
  this.updateMapLayers();
}

onMapReady(map: L.Map): void {
  this.map = map;
  L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    maxZoom: 19,
    attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
  }).addTo(map);

  this.updateMapLayers();
}

private updateMapLayers(): void {
  if (!this.map) return;

  if (this.geoJsonLayer) {
    this.map.removeLayer(this.geoJsonLayer);
  }
   // Remove existing legend control if it exists
   if (this.legendControl) {
    this.map.removeControl(this.legendControl);
    this.legendControl = undefined;
  }

  this.http.get<any>(this.jsonUrl).subscribe((data: any) => {
    if (data && data.features) {
      this.createChoroplethMap(this.map!, data.features); // Use non-null assertion operator
    }
  });
}
private addLegendControl(map: L.Map, minOrders: number, maxOrders: number, getColor: (ordersCount: number) => string): void {
  const generateLegendHTML = (): string => {
    let html = `<div style="display: flex; flex-direction: column; align-items: center;">`;
    html += `<div style="margin-bottom: 10px; color: black; font-size: 12px; font-weight: bold;">Orders Count</div>`;

    const legendItems = [
      { threshold: minOrders, color: getColor(minOrders) },
      { threshold: maxOrders, color: getColor(maxOrders) }
    ];

    legendItems.forEach(item => {
      html += `
        <div style="display: flex; align-items: center; margin-bottom: 5px;">
          <div style="background-color: ${item.color}; width: 20px; height: 20px; margin-right: 5px;"></div>
          <span style="font-size: 12px;">${item.threshold} orders </span>
        </div>`;
    });

    html += '</div>';
    return html;
  }

  const LegendControl = L.Control.extend({
    options: {
      position: 'bottomright'
    },
    onAdd: function (map: L.Map) {
      const div = L.DomUtil.create('div', 'legend');
      div.innerHTML = generateLegendHTML();
      return div;
    }
  });

  this.legendControl = new LegendControl();
  this.legendControl.addTo(map);
}

createChoroplethMap(map: L.Map, geoData: any): void {
  console.log("locationMap", this.locationMap);

  const ordersCounts = Object.values(this.locationMap).map(data => data.Orders_count);
  const minOrders = Math.min(...ordersCounts);
  const maxOrders = Math.max(...ordersCounts);

  console.log("Min Orders Count:", minOrders);
  console.log("Max Orders Count:", maxOrders);

  const getColor = (ordersCount: number): string => {
    const colorScale = d3.scaleLinear<string>()
      .domain([minOrders, maxOrders])
      .range(['#FFEDA0', '#800026']); // from light yellow to dark red
    return colorScale(ordersCount);
  }

  const style = (feature: any): L.PathOptions => {
    const formalEnNormalized = unorm.nfkd(feature.properties.SOVEREIGNT.toLowerCase());
    const foundLocation = this.UniqueShipmentLocations?.find(location =>
      unorm.nfkd(location.toLowerCase()).includes(formalEnNormalized)
    );

    const locationData = foundLocation ? this.locationMap[foundLocation] : undefined;

    if (!locationData) {
      return {
        fillColor: 'white',
        weight: 1,
        opacity: 1,
        color: 'white',
        fillOpacity: 0
      };
    } else {
      const ordersCount = locationData.Orders_count;
      const fillColor = getColor(ordersCount);

      return {
        fillColor: fillColor,
        weight: 1,
        opacity: 1,
        color: fillColor,
        fillOpacity: 0.7
      };
    }
  };

  const onEachFeature = (feature: any, layer: L.Layer): void => {
    let originalFillColor: string | undefined;
    let originalFillOpacity: number | undefined;

    layer.on({
      mouseover: (e: { target: any }) => {
        const layer = e.target;
        if (feature.properties && feature.properties.SOVEREIGNT) {
          const formalEnNormalized = unorm.nfkd(feature.properties.SOVEREIGNT.toLowerCase());
          const foundLocation = this.UniqueShipmentLocations?.find(location =>
            unorm.nfkd(location.toLowerCase()).includes(formalEnNormalized)
          );

          if (foundLocation) {
            const popupContent = `
              Country: ${foundLocation}<br>
              Orders Count: ${this.globalService.formatNumber(this.locationMap[foundLocation].Orders_count)}<br>
              Shipment price : ${this.locationMap[foundLocation].price}<br>
              Total Revenue: ${this.globalService.formatNumber(this.locationMap[foundLocation].Total)}$<br>
            `;
            layer.bindPopup(popupContent);
          } else {
            layer.bindPopup("No information found for this location.");
          }
        } else {
          layer.bindPopup("Country not available for this feature.");
        }

        originalFillColor = layer.options.fillColor;
        originalFillOpacity = layer.options.fillOpacity;

        layer.setStyle({
          fillColor: this.globalService.getRandomColor(),
          fillOpacity: 0.2,
        });
      },
      mouseout: (e: { target: any }) => {
        const layer = e.target;
        layer.setStyle({
          fillColor: originalFillColor,
          fillOpacity: originalFillOpacity,
        });
      },
    });
  }

  this.geoJsonLayer = L.geoJSON(geoData, {
    style: style,
    onEachFeature: onEachFeature
  }).addTo(map);

  this.addLegendControl(map, minOrders, maxOrders, getColor);
}

// end map 










}





