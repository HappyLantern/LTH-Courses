clc
clear all

c = [1;
     1;
     1;
     1;
     1;
     1;
     1];
A = [1 0 0 1 1 1 1;
     1 1 0 0 1 1 1;
     1 1 1 0 0 1 1;
     1 1 1 1 0 0 1;
     1 1 1 1 1 0 0;
     0 1 1 1 1 1 0;
     0 0 1 1 1 1 1];
 b = [8;
      6;
      5;
      4;
      6;
      7;
      9];
 lb = [0;
       0;
       0;
       0;
       0;
       0;
       0];
   
 A = -A;
 b = -b;

 options = optimoptions('linprog', 'Algorithm', 'dual-simplex', 'Display', 'off');
 [x,fval,exitflag,output,lambda] = linprog(c', A, b, [], [], lb, [], [], options)