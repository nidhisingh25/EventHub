# ng-orderby-pipe
 angular orderby pipe for array of strings and objects

    orderBy:key:desc/asc (default asc)

1)Order an array of strings

    *ngFor="let value of values| orderBy

2)Order an array of objects

    *ngFor="let value of values| orderBy:'brand':'desc'"

3)Order an array of objects with nested object

    *ngFor="let value of values| orderBy:'config.memory'"


