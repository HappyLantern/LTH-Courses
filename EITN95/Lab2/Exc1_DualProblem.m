clc
clear all

c = [1500;
     1575;
     420];
A = [4 5 1;
     5 3 2];
b = [13;
     11];
lb = [0;
      0;
      0];

A = -A;
b = -b;

   

 %options = optimoptions('linprog', 'Algorithm', 'interior-point', 'Display', 'iter');
 %options = optimoptions('linprog', 'Algorithm', 'interior-point', 'Display', 'off');
 %options = optimoptions('linprog', 'Algorithm', 'dual-simplex', 'Display', 'iter');
 options = optimoptions('linprog', 'Algorithm', 'dual-simplex', 'Display', 'off');
 [x,fval,exitflag,output,lambda] = linprog(c', A, b, [], [], lb, [], [], options)