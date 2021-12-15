clear;
conf_intervals(1,1:3) = load('conf_intervals_0.m');
conf_intervals(2,1:3) = load('conf_intervals_1.m');
conf_intervals(3,1:3) = load('conf_intervals_2.m');
conf_intervals(:,4) = [1;2;3];
figure
plot(conf_intervals(:,4),conf_intervals(:,2));
hold on
for i = 1:length(conf_intervals(:,4))
   plot([conf_intervals(i,4) conf_intervals(i,4)],[conf_intervals(i,1) conf_intervals(i,3)], 'r', 'LineWidth', 5); 
end
title('Time until all students have met');
xlabel('Experiment')
ylabel('Time (s)') 
legend({'simulation', 'confidence interval'},'Location','northeast')

times_1 = load('times_0.m');
times_2 = load('times_1.m');
times_3 = load('times_2.m');
figure
plot(times_1, 'linewidth', 2);
title('Frequency of time spent with another student, Experiment 1');
xlabel('Time spent (min)')
ylabel('Frequency') 
figure
plot(times_2, 'linewidth', 2);
title('Frequency of time spent with another student, Experiment 2');
xlabel('Time spent (min)')
ylabel('Frequency') 
figure
plot(times_3, 'linewidth', 2);
title('Frequency of time spent with another student, Experiment 3');
xlabel('Time spent (min)')
ylabel('Frequency') 