clear
tp_1 = load('throughput_1.m');
figure
plot(tp_1(:,2), tp_1(:,1));
hold on

for i = 1:length(tp_1(:,3))
    theory(i) = tp_1(i,2)*(1/4000)*exp(-2*(tp_1(i,2)*1/4000));
end
plot(tp_1(:,2),theory(1,:));
title('Throughput over network traffic, radius = 7000');
xlabel('Number of sensors')
ylabel('Throughput') 
legend({'simulation', 'formula'},'Location','northeast')

figure
hold on
cf_1 = load('conf_intervals_1.m');
plot(cf_1(:,4),cf_1(:,2));
for i = 1:length(cf_1(:,4))
   plot([cf_1(i,4) cf_1(i,4)], [cf_1(i,1) cf_1(i,3)], 'r', 'LineWidth', 5);
end
title('Packet loss over network traffic, radius = 7000');
xlabel('Number of sensors')
ylabel('Packet loss') 
legend({'simulation', 'confidence interval'},'Location','southeast')

figure
hold on
tp_2 = load('throughput_2.m');
plot(tp_1(:,2), tp_1(:,1));
plot(tp_2(:,2), tp_2(:,1));
title('Throughput over network traffic, radius = 7000');
xlabel('Number of sensors')
ylabel('Throughput') 
legend({'strategy 1', 'strategy 2'},'Location','northeast')


figure
hold on
cf_2 = load('conf_intervals_2.m');
plot(cf_1(:,4),cf_1(:,2));
plot(cf_2(:,4),cf_2(:,2));
for i = 1:length(cf_1(:,4))
    plot([cf_1(i,4) cf_1(i,4)], [cf_1(i,1) cf_1(i,3)], 'r', 'LineWidth', 5);
    plot([cf_2(i,4) cf_2(i,4)], [cf_2(i,1) cf_2(i,3)], 'g', 'LineWidth', 5);
end
title('Packet loss over network traffic, radius = 7000');
xlabel('Number of sensors')
ylabel('Packet loss') 
legend({'simulation - strategy 1', 'simulation - strategy 2', 'confidence interval - strategy 1', 'confidence interval - strategy 2'},'Location','southeast')