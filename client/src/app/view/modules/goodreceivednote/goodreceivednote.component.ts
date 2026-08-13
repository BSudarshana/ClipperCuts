import {Component,OnInit,ViewChild} from '@angular/core';
import {FormArray,FormBuilder,FormGroup,Validators} from '@angular/forms';
import {MatDialog} from '@angular/material/dialog';
import {MatPaginator} from '@angular/material/paginator';
import {MatTableDataSource} from '@angular/material/table';
import {GoodReceivedNote,GrnCreateRequest,GrnLookup,GrnPurchaseOrder,GrnReceiptLine} from '../../../entity/goodreceivednote';
import {GoodReceivedNoteService} from '../../../service/goodreceivednoteservice';
import {AuthorizationManager} from '../../../service/authorizationmanager';
import {ConfirmComponent} from '../../../util/dialog/confirm/confirm.component';
import {MessageComponent} from '../../../util/dialog/message/message.component';

@Component({
  selector:'app-goodreceivednote',
  templateUrl:'./goodreceivednote.component.html',
  styleUrls:['./goodreceivednote.component.css']
})

export class GoodreceivednoteComponent implements OnInit {
  form:FormGroup;
  purchaseOrders:GrnPurchaseOrder[]=[];
  locations:GrnLookup[]=[];
  receiptLines:GrnReceiptLine[]=[];

  grns:GoodReceivedNote[]=[];
  historyData=new MatTableDataSource<GoodReceivedNote>([]);
  selectedGrn:GoodReceivedNote|null=null;

  lineColumns=['itemnumber','itemName','ordered','previouslyReceived','remaining','received','unitType','unitCost','subTotal'];

  historyColumns=['grnNumber','date','poNumber','supplier','location','status','totalAmount','receivedBy','actions'];

  detailColumns=['itemnumber','itemName','quantity','unitCost','subTotal'];

  loading=false;
  loadingHistory=false;
  loadingDetail=false;
  saving=false;
  hasInsertAuthority=false;

  @ViewChild(MatPaginator) paginator!:MatPaginator;
  constructor(
    private fb:FormBuilder,
    private service:GoodReceivedNoteService,
    private dialog:MatDialog,
    public authService:AuthorizationManager
  ){
    this.form=this.fb.group({
      purchaseOrderId:[null,Validators.required],
      locationId:[null,Validators.required],
      description:['',[Validators.maxLength(500)]],
      quantities:this.fb.array([])
    });
  }

  ngOnInit():void{
    this.initializeAuthority();
    this.loadInitialData();
  }

  get quantities():FormArray{
    return this.form.get('quantities') as FormArray;
  }

  get selectedPo():GrnPurchaseOrder|null{
    return this.purchaseOrders.find(x=>x.id===Number(
      this.form.get('purchaseOrderId')?.value))||null;
  }

  get totalAmount():number{
    return Number(
      this.receiptLines.reduce((s,x)=>s+x.subTotal,0).toFixed(2)
    );
  }

  private initializeAuthority():void{
    const a=this.authService.getAuthorities();
    if(Array.isArray(a)){
      const values=this.authService.extractAuthorities(a);
      this.hasInsertAuthority=values.some((x:{module:string;operation:string})=>x.module==='goodreceivednote'&&x.operation==='insert');
    }
  }

  private loadInitialData():void{
    this.loading = true;
    let pendingRequests = 2;

    const requestCompleted = (): void => {
      pendingRequests--;
      if (pendingRequests === 0) {
        this.loading = false;
      }
    };

    this.service.getEligiblePurchaseOrders().subscribe({
      next: purchaseOrders => {
        this.purchaseOrders = purchaseOrders || [];
        requestCompleted();
      },
      error: error => {
        this.purchaseOrders = [];
        requestCompleted();
        this.handleError(error, 'Unable to load eligible purchase orders.');
      }
    });

    this.service.getLocations().subscribe({
      next: locations => {
        this.locations = locations || [];
        requestCompleted();
      },
      error: error => {
        this.locations = [];
        requestCompleted();
        this.handleError(error, 'Unable to load inventory locations.');
      }
    });

    this.loadHistory();
  }
  onPurchaseOrderChange():void{this.quantities.clear();const po=this.selectedPo;
    this.receiptLines=(po?.items||[]).map(item=>({...item,receivedQuantity:0,subTotal:0}));
    this.receiptLines.forEach(line=>{
      const control=this.fb.control(null,[Validators.min(0),Validators.max(Number(line.remainingQuantity))]);
      control.valueChanges.subscribe(v=>{line.receivedQuantity=Number(v||0);
        line.subTotal=Number((line.receivedQuantity*Number(line.unitCost)).toFixed(2));
      });
      this.quantities.push(control);
    });
  }
  useRemaining(index:number):void{this.quantities.at(index).setValue(Number(this.receiptLines[index].remainingQuantity));}
  submit():void{
    this.form.markAllAsTouched();

    if (this.form.get('purchaseOrderId')?.hasError('required')) {
      return this.showMessage(
        'Errors - Goods Received Note',
        'Select a purchase order.'
      );
    }

    if (this.form.get('locationId')?.hasError('required')) {
      return this.showMessage(
        'Errors - Goods Received Note',
        'Select an inventory location.'
      );
    }

    if (this.form.get('description')?.invalid) {
      return this.showMessage(
        'Errors - Goods Received Note',
        'Description cannot exceed 500 characters.'
      );
    }

    if (this.quantities.controls.some(control => control.invalid)) {
      return this.showMessage(
        'Errors - Goods Received Note',
        'A received quantity cannot be negative or exceed the remaining quantity.'
      );
    }

    const items=this.receiptLines.filter(x=>x.receivedQuantity>0);

    if(!items.length)
      return this.showMessage('Errors - Goods Received Note','Enter a received quantity for at least one product.');
    const po=this.selectedPo!;
    this.confirm(
      'Confirmation - Goods Received Note',
      `Are you sure to receive ${items.length}
        product line(s)?<br><br>PO: ${po.poNumber}<br>Total: ${this.totalAmount.toFixed(2)}`,()=>this.save(items)
    );
  }

  private save(items:GrnReceiptLine[]):void{
    const v=this.form.getRawValue();
    const request:GrnCreateRequest={
      purchaseOrderId:Number(v.purchaseOrderId),
      locationId:Number(v.locationId),
      description:(v.description||'').trim(),
      items:items.map(x=>(
        {
          poItemId:x.poItemId,
          receivedQuantity:x.receivedQuantity
        })
      )
    };
    this.saving=true;
    this.service.create(request).subscribe({
      next:r=>{
        this.saving=false;this.reset();
        this.loadInitialData();
        this.showMessage(
          'Status - Goods Received Note',
          `${r.grnNumber} created successfully.`
        );
      },
      error:e=>{this.saving=false;
        this.handleError(e,'Goods receipt could not be completed.');
      }
    });
  }
  clear():void{if(!this.form.dirty)return this.reset();
    this.confirm('Confirmation - GRN Clear','Are you sure to clear the entered goods receipt details?',()=>this.reset());
  }

  private reset():void{
    this.form.reset({description:''});
    this.quantities.clear();this.receiptLines=[];
    this.selectedGrn=null;
  }

  loadHistory():void{
    this.loadingHistory=true;
    this.service.getAll()
      .subscribe({next:x=>{
          this.grns=x||[];
          this.historyData=new MatTableDataSource(this.grns);
          this.historyData.paginator=this.paginator;
          this.loadingHistory=false;
        },
        error:e=>{
          this.loadingHistory=false;
          this.handleError(e,'Unable to load GRN history.');
        }
      });
  }

  view(id:number):void{
    this.loadingDetail=true;
    this.service.getById(id).subscribe({next:x=>{this.selectedGrn=x;this.loadingDetail=false;
      },
      error:e=>{
        this.loadingDetail=false;
        this.handleError(e,'Unable to load GRN details.');
      }
    });
  }

  private handleError(e:any,fallback:string):void{
    this.showMessage('Goods Received Note - Error',e?.error?.message||e?.error?.error||fallback);
  }

  private showMessage(heading:string,message:string):void{
    this.dialog.open(MessageComponent,{
      width:'500px',
      data:{heading,message}
    });
  }

  private confirm(heading:string,message:string,action:()=>void):void{
    this.dialog.open(ConfirmComponent,{
      width:'500px',
      data:{heading,message}}).afterClosed().subscribe(r=>{if(r)action();
    });
  }
}
