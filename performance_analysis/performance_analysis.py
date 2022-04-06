import subprocess
from timeit import default_timer as timer
from memory_profiler import memory_usage


def run_java_program(program_name, program_args):
    '''
    Runs a java program with the given arguments.

    ARGS:
        program_name: the name of the java program to run
        program_args: the arguments to pass to the java program

    RETURNS:
        p: the process object
        stdout: the stdout of the java program
        stderr: the stderr of the java program
    '''
    command = ["java", program_name] + program_args.split()
    p = subprocess.Popen(command, stdout=subprocess.PIPE,
                         stderr=subprocess.PIPE)
    return p, p.stdout, p.stderr


def get_performance_stats(program_name, program_args):
    '''
    Runs a java program with the given arguments and returns the elapsed time.

    ARGS:
        program_name: the name of the java program to run
        program_args: the arguments to pass to the java program

    RETURNS:
        elapsed_time: the elapsed time of the java program
        mem_usage: the memory usage of the java program
    '''
    start = timer()
    p, _, _ = run_java_program(program_name, program_args)
    end = timer()
    elapsed_time = end - start
    mem_usage = memory_usage(
        proc=p.pid, max_usage=True, interval=0.1, timeout=1)
    return elapsed_time, mem_usage


def main():
    program = "HelloWorld.java"  # java program name
    args = ""  # java program arguments

    elapsed_time, mem_usage = get_performance_stats(program, args)

    print(f"Elapsed time: {elapsed_time}\nMemory usage: {mem_usage}MiB")


if __name__ == "__main__":
    main()
