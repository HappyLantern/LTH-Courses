figure
hold on
tp_2 = load('throughput_2.m');
plot(tp_2(:,3), tp_2(:,1));
title('Throughput over radius, n = 2000');
xlabel('Radius')
ylabel('Throughput') 



