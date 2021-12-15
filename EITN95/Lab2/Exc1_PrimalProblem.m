clc
clear all

c = [13;
     11];
A = [4 5;
     5 3;
     1 2];
b = [1500;
     1575;
     420];
lb = [0;
      0];
   
c = -c;
 %options = optimoptions('linprog', 'Algorithm', 'interior-point', 'Display', 'iter');
 %options = optimoptions('linprog', 'Algorithm', 'interior-point', 'Display', 'off');
 %options = optimoptions('linprog', 'Algorithm', 'dual-simplex', 'Display', 'iter');
 options = optimoptions('linprog', 'Algorithm', 'dual-simplex', 'Display', 'off');
 [x,fval,exitflag,output,lambda] = linprog(c', A, b, [], [], lb, [], [], options)