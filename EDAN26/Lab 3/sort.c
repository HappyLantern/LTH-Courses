
#include <assert.h>
#include <limits.h>
#include <pthread.h>
#include <stddef.h>
#include <stdlib.h>
#include <stdio.h>
#include <sys/times.h>
#include <sys/time.h>
#include <unistd.h>
#include <string.h>

#define MAX_THREADS 4

#define PARALLEL

struct quick_args {
        double *A;
        int lo;
        int hi;
        int threads;
};

static double sec(void)
{
	struct timeval t;
	gettimeofday(&t, NULL);
	return t.tv_sec + (t.tv_usec/1000000.0);
}


int partition(double *A, int lo, int hi)
{
        double pivot = A[lo];
        int i = lo - 1;
        int j = hi + 1;
        while (1) {
                do {
                        --j;
                } while (A[j] > pivot);
                do {
                        ++i;
                } while (A[i] < pivot);
                if (i < j) {
                        double tmp = A[i];
                        A[i] = A[j];
                        A[j] = tmp;
                } else {
                        return j;
                }
        }
}

void *quick(void *ap)
{
        struct quick_args *a = ap;
        double *A = a->A;
        int lo = a->lo;
        int hi = a->hi;
        int threads = a->threads;
        if (lo < hi) {
                int p = partition(A, lo, hi);
                struct quick_args a1 = {A, lo, p, threads/2};
                pthread_t t;
                if (threads > 1) {
                        pthread_create(&t, NULL, quick, &a1);
                } else {
                        quick(&a1);
                }
                struct quick_args a2 = {A, p + 1, hi, threads/2};
                quick(&a2);
                if (threads > 1) {
                        pthread_join(t, NULL);
                }
        }
        return NULL;
}

int main(int ac, char** av)
{
        int             n = 2000000;
        int             i;
        double*         a;
        double          start, end;

        if (ac > 1)
                sscanf(av[1], "%d", &n);

        srand(getpid());

        a = malloc(n * sizeof a[0]);
        for (i = 0; i < n; i++) {
                a[i] = rand();
        }

        start = sec();

#ifdef PARALLEL
        struct quick_args sa = {a, 0, n-1, MAX_THREADS};
        quick(&sa);
#else
        qsort(a, n, sizeof a[0], cmp);
#endif

        end = sec();

        for (i = 0; i < n-1; ++i) {
                int res = a[i] <= a[i+1];
                assert(res);
        }

        printf("%1.2f s\n", end - start);

        free(a);

        return 0;
}
