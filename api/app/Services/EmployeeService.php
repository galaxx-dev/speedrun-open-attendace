<?php
namespace App\Services;

use App\Models\Employee;
use App\Repositories\EmployeeRepository;


class EmployeeService
{
    public function getAllData()
    {
        return (new EmployeeRepository())->getAllDataPaginated();
    }

    public function getDataById(string $id): ? Employee
    {
        return (new EmployeeRepository())->getDataEmployeeById($id);
    }

    public function updateDataById(string $id, array $requestedData): int
    {
        return (new EmployeeRepository())->updateDataEmployeeById($id, $requestedData);
    }
}

?>
