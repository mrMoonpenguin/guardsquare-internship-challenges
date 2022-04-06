import random
import string
# random python package that I found that seemed very useful for random package name generation
from barnum import gen_data


def random_package_names(n):
    '''
    Generates a list with n random valid Android package names.
    Android package names are of the form 'com.companyname.applicationname'

    ARGS:
        n: the number of package names to generate

    RETURNS:
        package_names: a list of n random valid Android package names
    '''
    package_names = []
    for _ in range(n):
        # random latin word, barnum does not have a function for random latin words so I just create a sentence and take the first word
        # also, using latin words as company name sounds very hipster
        company_name = gen_data.create_sentence().split(" ")[0].lower()
        application_name = gen_data.create_nouns().split(" ")[0]
        package_name = f"com.{company_name}.{application_name}"
        package_names.append(package_name)

    return package_names


print(random_package_names(100))
