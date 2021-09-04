import sys
from os.path import dirname, join
from com.chaquo.python import Python


# get data here
def main(codeAreaData):
    
    # here we will first create text file then execute code and save output of that code int txt file...
    # and finally return text file output data to java code and display in our textview

    # give directory path for txt file
    file_dir = str(Python.getPlatform().getApplication().getFilesDir())

    # give file name
    file_name = join(dirname(file_dir), 'file.txt')

    # now to execute our code we will use stdout concept here...
    try:
        # first save a reference to the original standard output
        original_stdout = sys.stdout

        # now open new file (file.txt) with intention to write data and change standard output to our file
        sys.stdout = open(file_name, 'w', encoding = 'utf8', errors="ignore")

        # now execute our code using exec() method
        exec(codeAreaData) # it will execute our code and save output in file

        # example =>        exec("""print("Hello world")""")        output => Hello world  --we will write in the file

        # now close the file after writing data
        sys.stdout.close()

        # reset the standard output to its original value
        sys.stdout = original_stdout

        # open file and read output and save in variable
        output = open(file_name, 'r').read()
        
    except Exception as e:
        # to handle errors
        # if any error occur in the code like suntax error then take the error message
        # in the output variable to show on screen

        sys.stdout = original_stdout

        # take exception error in output
        output = e

    # finally return output
    return str(output)
