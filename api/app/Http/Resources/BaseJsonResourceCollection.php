<?php

namespace App\Http\Resources;

use Illuminate\Http\Resources\Json\ResourceCollection;

class BaseJsonResourceCollection extends ResourceCollection
{
    public static $wrap = 'payload';
}
?>
