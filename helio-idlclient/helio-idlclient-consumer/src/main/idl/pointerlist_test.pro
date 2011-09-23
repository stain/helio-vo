;+
;Small demo program to show the pointer problem with linkedlist.
;
;The pointer x is added to the list and removed from it.
;After that the pointer is invalid without the /nofree keyword.
;
;

pro pointerlist_test, nofree=nofree
  ;create a linked list and a pointer
  list = obj_new('linkedlist')
  x = ptr_new(5)
  
  ;print the value of the pointer
  print, *x
  
  ;add the pointer to the list and delete it from the list.
  list->add, x
  list->DELETE, 0, nofree=nofree
  
  ;print the pointe again.
  print, *x
  
end