# NgxOrderBy

Order by multpile fields and directions pipe for Angular >= 2

## How to install

Install package with npm

`npm install ngx-order-by`

Import in your module file

```ts
import {NgxOrderBy} from 'ngx-order-by';
```

And then include this module in import section in @NgModule

```ts
imports: [
    NgxOrderBy
    ...
]
```

## How to use

```html
<li *ngFor="let item of items | ngxOrderBy:orderFields:orderReverse:changeIndicator;">
  ...
</li>
```

* `orderFields` - array of fields to order by (Array<string>)
* `orderReverse` - boolean value defines order direction. If true then reverse
* `changeIndicator` - optional parameter (number) to handle pipe update (for e.g. when adding or removing 
element form items array)
